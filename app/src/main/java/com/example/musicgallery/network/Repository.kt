package com.example.musicgallery.network

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import com.example.musicgallery.interfaces.RepositoryInterface
import com.example.musicgallery.model.MusicList
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class Repository @Inject constructor(
    private val router: Router,
    private val connectivityManager: ConnectivityManager,
    private val networkRequest: NetworkRequest
): RepositoryInterface {
    override val networkState: BehaviorSubject<Boolean> =
        BehaviorSubject.create()

    override fun registerNetworkAvailability() {
        // The callback doesn't check if WiFi/Data is turned on or not.
        if (connectivityManager.activeNetwork == null)
            networkState.onNext(false)
        connectivityManager
            .requestNetwork(networkRequest, networkCallback)
    }

    override fun unregisterNetworkAvailability() {
        connectivityManager
            .unregisterNetworkCallback(networkCallback)
    }

    override fun getData(musicType: String): Single<MusicList> {
        return router.getMusic(musicType = musicType)
    }


    private val networkCallback by lazy {
        object: ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                networkState.onNext(true)
            }

            override fun onUnavailable() {
                super.onUnavailable()
                networkState.onNext(false)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                networkState.onNext(false)
            }
        }
    }
}