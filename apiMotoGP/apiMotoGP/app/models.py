from mongoengine import Document, EmbeddedDocument, fields
 


class Carreras(Document):
    temporada = fields.IntField(required=False)
    categoria = fields.StringField(required=False)
    abreviatura = fields.StringField(required=False)
    titulo = fields.StringField(required=False)
    lugar = fields.StringField(required=False)
    fecha = fields.StringField(required=False)
    pos = fields.IntField(required=False)
    puntos = fields.IntField(required=False)
    num = fields.IntField(required=False)
    piloto = fields.StringField(required=False)
    pais = fields.StringField(required=False)
    equipo = fields.StringField(required=False)
    moto = fields.StringField(required=False)
    kmh = fields.FloatField(required=False)
    diferencia = fields.StringField(required=False)

class Campeonatos(Document):
    temporada = fields.IntField(required=False)
    categoria = fields.StringField(required=False)
    pos = fields.IntField(required=False)
    piloto = fields.StringField(required=False)
    moto = fields.StringField(required=False)
    pais = fields.StringField(required=False)
    puntos = fields.IntField(required=False)

class Documentacion(Document):
    temporada = fields.IntField(required=False)
    categoria = fields.StringField(required=False)
    abreviatura = fields.StringField(required=False)
    titulo = fields.StringField(required=False)
    lugar = fields.StringField(required=False)
    fecha = fields.StringField(required=False)
    documentacion = fields.ListField()
    
   
    


