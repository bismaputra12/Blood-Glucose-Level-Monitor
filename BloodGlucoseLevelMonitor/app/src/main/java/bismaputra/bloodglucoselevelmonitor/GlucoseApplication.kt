package bismaputra.bloodglucoselevelmonitor

import android.app.Application

class GlucoseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        // this file initializes the GlucoseRepository class/file
        GlucoseRepository.initialize(this)
    }
}