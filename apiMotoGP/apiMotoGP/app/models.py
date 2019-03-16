from mongoengine import Document, EmbeddedDocument, fields
import time
from django.db.models import F


class Carreras(Document):
    id=fields.ObjectId
    temporada = fields.IntField(required=False)
    categoria = fields.StringField(required=False)
    abreviatura = fields.StringField(required=False)
    titulo = fields.StringField(required=False)
    lugar = fields.StringField(required=False)
    fecha = fields.DateTimeField(required=False)
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
    id=fields.ObjectId
    temporada = fields.IntField(required=False)
    categoria = fields.StringField(required=False)
    pos = fields.IntField(required=False)
    piloto = fields.StringField(required=False)
    moto = fields.StringField(required=False)
    pais = fields.StringField(required=False)
    puntos = fields.IntField(required=False)

class Documentacion(Document):
    id=fields.ObjectId
    temporada = fields.IntField(required=False)
    categoria = fields.StringField(required=False)
    abreviatura = fields.StringField(required=False)
    titulo = fields.StringField(required=False)
    lugar = fields.StringField(required=False)
    fecha = fields.StringField(required=False)
    documentacion = fields.ListField()