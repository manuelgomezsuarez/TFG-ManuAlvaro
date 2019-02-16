from django.conf.urls import url
from rest_framework_mongoengine import routers as merouters
from app.views import PosicionCarreraViewSet,PosicionCampeonatoViewSet,PosicionDocumentacionViewSet,DistintosViewSet,index
from django.urls import path

merouter = merouters.DefaultRouter()
merouter.register(r'carrera', PosicionCarreraViewSet,base_name="carrera")
merouter.register(r'campeonato', PosicionCampeonatoViewSet,base_name="campeonato")
merouter.register(r'documentacion', PosicionDocumentacionViewSet,base_name="documentacion")
merouter.register(r'distintos', DistintosViewSet,base_name="distintos")


urlpatterns = [
    path('', index, name='index'),

]
 
urlpatterns += merouter.urls