from django.shortcuts import render
from rest_framework_mongoengine import viewsets as meviewsets
from apiMotoGP.serializers import PosicionCarreraSerializer, PosicionCampeonatoSerializer, PosicionDocumentacionSerializer
from app.models import  Carreras, Campeonatos,Documentacion
import django_filters.rest_framework

from django_filters.rest_framework import DjangoFilterBackend

class PosicionCarreraViewSet(meviewsets.ModelViewSet):
    serializer_class = PosicionCarreraSerializer
    def get_kwargs_for_filtering(self):
        filtering_kwargs = {} 
        my_filter_fields = ('piloto', 'num','temporada','categoria','abreviatura','titulo','lugar','fecha','pos','puntos','pais','equipo','moto','kmh')
        for field in  self.request.query_params: # iterate over the filter fields
            if field.split("__")[0] in my_filter_fields:
                field_value = self.request.query_params.get(field) # get the value of a field from request query parameter
                if field_value: 
                    filtering_kwargs[field] = field_value
        return filtering_kwargs 

    def get_queryset(self):
        queryset = Carreras.objects.all() 
        filtering_kwargs = self.get_kwargs_for_filtering() # get the fields with values for filtering 
        if filtering_kwargs:
            queryset = Carreras.objects.filter(**filtering_kwargs) # filter the queryset based on 'filtering_kwargs'
        return queryset




class PosicionCampeonatoViewSet(meviewsets.ModelViewSet):
    serializer_class = PosicionCampeonatoSerializer

    def get_kwargs_for_filtering(self):
        filtering_kwargs = {} 
        my_filter_fields = ('temporada', 'categoria','pos','piloto','moto','pais','puntos')
        for field in  self.request.query_params: # iterate over the filter fields
            if field.split("__")[0] in my_filter_fields:
                field_value = self.request.query_params.get(field) # get the value of a field from request query parameter
                if field_value: 
                    filtering_kwargs[field] = field_value
        return filtering_kwargs 

    def get_queryset(self):
        queryset = Campeonatos.objects.all() 
        filtering_kwargs = self.get_kwargs_for_filtering() # get the fields with values for filtering 
        if filtering_kwargs:
            queryset = Campeonatos.objects.filter(**filtering_kwargs) # filter the queryset based on 'filtering_kwargs'
        return queryset



class PosicionDocumentacionViewSet(meviewsets.ModelViewSet):
    serializer_class = PosicionDocumentacionSerializer

    def get_kwargs_for_filtering(self):
        filtering_kwargs = {} 
        my_filter_fields = ('temporada', 'categoria','abreviatura','titulo','lugar','fecha')
        for field in  self.request.query_params: # iterate over the filter fields
            if field.split("__")[0] in my_filter_fields:
                field_value = self.request.query_params.get(field) # get the value of a field from request query parameter
                if field_value: 
                    filtering_kwargs[field] = field_value
        return filtering_kwargs 

    def get_queryset(self):
        queryset = Documentacion.objects.all() 
        filtering_kwargs = self.get_kwargs_for_filtering() # get the fields with values for filtering 
        if filtering_kwargs:
            queryset = Documentacion.objects.filter(**filtering_kwargs) # filter the queryset based on 'filtering_kwargs'
        return queryset
