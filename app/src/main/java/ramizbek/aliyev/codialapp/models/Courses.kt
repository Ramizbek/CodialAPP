package ramizbek.aliyev.codialapp.models

class Courses {
    var id: Int? = null
    var name: String? = null
    var description: String? = null

    constructor(id: Int?, name: String?, description: String?) {
        this.id = id
        this.name = name
        this.description = description
    }

    constructor(name: String?, description: String?) {
        this.name = name
        this.description = description
    }

    constructor()

}