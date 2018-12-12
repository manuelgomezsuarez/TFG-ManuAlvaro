
import django_filters

from app.models import Campeonatos

class CampeonatoFilter(django_filters.FilterSet):
    temporada = django_filters.CharFilter(lookup_expr='exact')
    categoria = django_filters.CharFilter(lookup_expr='exact')
    pos = django_filters.NumberFilter()
    piloto = django_filters.CharFilter(lookup_expr='icontains')
    moto = django_filters.CharFilter(lookup_expr='icontains')
    pais = django_filters.CharFilter(lookup_expr='icontains')
    puntos =  django_filters.NumberFilter()
    #class Meta:
    #    model = Campeonatos
    #    fields= ['temporada','categoria','pos','moto','pais','puntos']


