from mongoengine import Document, EmbeddedDocument, fields
 


class Carreras(Document):
    temporada = fields.StringField(required=False)
    categoria = fields.StringField(required=False)
    abreviatura = fields.StringField(required=False)
    titulo = fields.StringField(required=False)
    lugar = fields.StringField(required=False)
    fecha = fields.StringField(required=False)
    pos = fields.StringField(required=False)
    puntos = fields.StringField(required=False)
    num = fields.StringField(required=False)
    piloto = fields.StringField(required=False)
    pais = fields.StringField(required=False)
    equipo = fields.StringField(required=False)
    moto = fields.StringField(required=False)
    kmh = fields.StringField(required=False)
    diferencia = fields.StringField(required=False)

class Campeonatos(Document):
    temporada = fields.StringField(required=False)
    categoria = fields.StringField(required=False)
    pos = fields.StringField(required=False)
    piloto = fields.StringField(required=False)
    moto = fields.StringField(required=False)
    pais = fields.StringField(required=False)
    puntos = fields.StringField(required=False)


class Documentacion(Document):
    temporada = fields.StringField(required=False)
    categoria = fields.StringField(required=False)
    abreviatura = fields.StringField(required=False)
    titulo = fields.StringField(required=False)
    lugar = fields.StringField(required=False)
    fecha = fields.StringField(required=False)
    documentacion = fields.ListField()
    
   
    


