// Place your Spring DSL code here
import  server.Publicacion
import grails.rest.render.json.JsonCollectionRenderer
beans = {
    jsonBookCollectionRenderer(JsonCollectionRenderer, Publicacion)
}
