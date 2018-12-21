package com.c4coding.forecastweather.ui.weather.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.request.RequestOptions
import com.c4coding.forecastweather.R
import com.c4coding.forecastweather.data.network.ConnectionIntercepterImpl
import com.c4coding.forecastweather.data.network.NetWorkWeatherSourceImpl
import com.c4coding.forecastweather.data.network.response.ServicesWeatherApi
import com.c4coding.forecastweather.ui.base.FragmentScope
import com.c4coding.forecastweather.ui.glide.GlideApp
import kotlinx.android.synthetic.main.current_weatehr_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class CurrentWeatehrFrag : FragmentScope(),KodeinAware {

    override val kodein by closestKodein()

    val viewmodelfactory : CurrentViewModelFactory by instance()


    private lateinit var viewModel: CurrentWeatehrViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weatehr_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this,viewmodelfactory)
            .get(CurrentWeatehrViewModel::class.java)

        onInitilizeUI()
    }
    fun onInitilizeUI() = launch{
        viewModel.weather.await()
            .observe(this@CurrentWeatehrFrag, Observer {
                if (it == null) return@Observer

                groupId.visibility=View.GONE
                setUpdateLocation("karachi")
                setUpdateDayDate()
                setUpdateTempFeelLike(it.temperature,it.feelsLikeTemperature)
                setUpdateCondition(it.conditionText)
                setPercipetation(it.percipitaitionValue)
                setWindDir(it.windDirection,it.winSpeed)
                GlideApp.with(this@CurrentWeatehrFrag)
                    .setDefaultRequestOptions(RequestOptions()
                        .placeholder(R.drawable.weathericons))
                    .load("http:${it.conditionUrlIcon}")
                    .into(imageView)
            })

    }
    private fun setUpdateLocation(location : String){
        (activity as? AppCompatActivity)?.supportActionBar?.title =location
    }
    private fun setUpdateDayDate(){
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle="Today"
    }
    private fun getSpacificUnit(matric : String ,imperial : String) : String{
        return if (viewModel.isMatric) matric else imperial
    }
    private fun setUpdateTempFeelLike(tempareture : Double,feelLike : Double){
        var unit=getSpacificUnit("°C","°F")
        tempature.text="$tempareture $unit"
        feellikeTemp.text="Feel Like :$feelLike $unit"
    }
    private fun setUpdateCondition(condition : String){
        rain_possible.text=condition
    }
    private fun setPercipetation(percipetati :Double){
        var unit=getSpacificUnit("mm","in")
        precipitaion.text="Precipitation : $percipetati $unit"
    }
    private fun setWindDir(windDir :String ,windSpeed :Double){
        wind_dir.text="Wind Direction : $windDir , $windSpeed"
    }
        //direct access network api response
     /*   val weather =ServicesWeatherApi(ConnectionIntercepterImpl(this.context!!))
        GlobalScope.launch(Dispatchers.Main) {
           try {
               val response =weather.getDetails("karachi").await()
               weatherview.text=response.currentWeather.toString()
           }catch (e : NoConnectivityExecption){
               weatherview.text ="Connection Failure"
               Log.e("NetworkError","No Internet",e)
           }
        }
*/



}
