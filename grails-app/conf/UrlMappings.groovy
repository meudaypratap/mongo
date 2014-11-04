class UrlMappings {

    static mappings = {
        "/$action?/$id?(.$format)?" {
            controller = "home"
        }

        "/signup" {
            controller = "home"
            action = "signup"
        }

        "/login" {
            controller = "home"
            action = "login"
        }
        "/logout" {
            controller = "home"
            action = "logout"
        }
        "/welcome" {
            controller = "home"
            action = "welcome"
        }
        "/" {
            controller = "home"
            action = "index"
        }
        "500"(view: '/error')
    }
}
