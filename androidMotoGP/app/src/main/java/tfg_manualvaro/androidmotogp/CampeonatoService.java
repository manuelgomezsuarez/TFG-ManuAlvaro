package tfg_manualvaro.androidmotogp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import tfg_manualvaro.androidmotogp.models.CampeonatoModelo;

public interface CampeonatoService {

    String API_ROUTE = "/campeonato/?format=json";

    @GET(API_ROUTE)
    Call< List<CampeonatoModelo> > getCampeonatos();

}