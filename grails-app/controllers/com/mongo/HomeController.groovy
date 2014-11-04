package com.mongo

import com.mongo.dao.BlogPostDAO
import com.mongo.dao.SessionDAO
import com.mongo.dao.UserDAO
import com.mongo.util.Util
import com.mongodb.DB
import com.mongodb.DBObject

import javax.servlet.http.Cookie

class HomeController {
    SessionDAO sessionDAO
    UserDAO userDAO
    BlogPostDAO blogPostDAO
    def beforeInterceptor = {
        DB blogDB = Util.getBlogDB()
        sessionDAO = new SessionDAO(blogDB)
        userDAO = new UserDAO(blogDB)
        blogPostDAO = new BlogPostDAO(blogDB)
    }

    def post(String id) {
        DBObject post = blogPostDAO.findByPermalink(id);
        println "ID---${id}---${post}"
        if (post == null) {
            render(view: "/notFound")
        } else {
            // empty comment to hold new comment in form at bottom of blog entry detail page
            Map newComment = [:]
            newComment.put("name", "");
            newComment.put("email", "");
            newComment.put("body", "");
            render(view: "post", model: [post: post, comment: newComment])
        }
    }

    def index() {
        List<DBObject> posts = blogPostDAO.findByDateDescending(10);
        [posts: posts]
    }

    def newcomment(String commentName, String commentEmail, String commentBody, String permalink) {

        String name = commentName
        String email = commentEmail
        String body = commentBody

        DBObject post = blogPostDAO.findByPermalink(permalink);
        if (post == null) {
            render(view: "/notFound")
        }
        // check that comment is good
        else if (!name || !body) {
            render(view: 'post', model: [post: post, comment: [name: name, email: email, body: body]])
        } else {
            blogPostDAO.addPostComment(name, email, body, permalink);
            redirect(action: "post", params: [id: permalink])
        }
    }

    def signup(String username, String password, String verify, String email) {
        if (request.post) {
            Map root = [username: username, email: email]
            if (validateSignup(username, password, verify, email, root)) {
                // good user
                System.out.println("Signup: Creating user with: " + username + " " + password);
                if (!userDAO.addUser(username, password, email)) {
                    // duplicate user
                    root.put("username_error", "Username already in use, Please choose another");
                    render(view: 'signup', model: root)
                } else {
                    // good user, let's start a session
                    session.username = username
                    redirect(action: 'welcome')
                }
            } else {
                render(view: 'signup', model: root)
            }
            return
        }

    }

    def welcome() {
        if (session.username) {
            render(view: 'welcome')
        } else {
            redirect(action: 'signup')
        }
    }

    def login(String username, String password) {
        if (request.post) {
            DBObject user = userDAO.validateLogin(username, password);
            if (user && user["_id"]) {
                session.username = user["_id"]
                redirect(action: 'welcome')
            } else {
                render(view: 'login', model: [login_error: "Invalid login", username: username, password: password])
            }
            return
        }
    }

    def logout() {
        session.invalidate()
        redirect(action: 'login')
    }

    def newpost(String subject, String tags, String body) {
        if (request.post) {
            if (!subject || !body) {
                println "---fail---$params"
                render(view: 'newpost', model: ['errors': 'post must contain a title and blog entry.', subject: subject, tags: tags, post: body])
            } else {
                println "---done---"
                // extract tags
                ArrayList<String> tagsArray = extractTags(tags);

                // substitute some <p> for the paragraph breaks
                body = body.replaceAll('\\r?\\n', '<p>');

                String permalink = blogPostDAO.addPost(subject, body, tagsArray, session.username);
                println "---id--${permalink}"
                // now redirect to the blog permalink
                redirect(action: 'post', params: [id: permalink])
            }
        }
    }

    private ArrayList<String> extractTags(String tags) {

        // probably more efficent ways to do this.
        //
        // whitespace = re.compile('\s')

        tags = tags.replaceAll("\\s", "");
        List tagArray = tags.split(",");

        // let's clean it up, removing the empty string and removing dups
        ArrayList<String> cleaned = new ArrayList<String>();
        for (String tag : tagArray) {
            if (!tag.equals("") && !cleaned.contains(tag)) {
                cleaned.add(tag);
            }
        }

        return cleaned;
    }

    private boolean validateSignup(String username, String password, String verify, String email, HashMap<String, String> errors) {
        String USER_RE = '^[a-zA-Z0-9_-]{3,20}$';
        String PASS_RE = '^.{3,20}$';
        String EMAIL_RE = '^[\\S]+@[\\S]+\\.[\\S]+$';

        errors.put("username_error", "");
        errors.put("password_error", "");
        errors.put("verify_error", "");
        errors.put("email_error", "");

        if (!username.matches(USER_RE)) {
            errors.put("username_error", "invalid username. try just letters and numbers");
            return false;
        }

        if (!password.matches(PASS_RE)) {
            errors.put("password_error", "invalid password.");
            return false;
        }


        if (!password.equals(verify)) {
            errors.put("verify_error", "password must match");
            return false;
        }

        if (!email.equals("")) {
            if (!email.matches(EMAIL_RE)) {
                errors.put("email_error", "Invalid Email Address");
                return false;
            }
        }

        return true;
    }

}
