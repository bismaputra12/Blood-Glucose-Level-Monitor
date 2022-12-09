package bismaputra.bloodglucoselevelmonitor

import android.content.Context
import androidx.room.Room
import bismaputra.bloodglucoselevelmonitor.database.GlucoseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.*

private const val DATABASE_NAME = "glucose-database.db"

// this repository file encapsulates the logic for accessing database
// from a single source or a set of sources (in simple term: to access the database)

//private constructor means that this construction method cannot
//be applied outside of this path definition
class GlucoseRepository private constructor(
    context: Context,
    private val coroutineScope: CoroutineScope = GlobalScope
){

    private val database: GlucoseDatabase = Room.databaseBuilder(
            context.applicationContext,
            GlucoseDatabase::class.java,
            DATABASE_NAME
        )
        .build()

    fun getGlucoses(): Flow<List<Glucose>> = database.glucoseDao().getGlucoses()

    suspend fun getGlucose(glucoseDate: Date): Glucose = database.glucoseDao().getGlucose(glucoseDate)

    fun updateGlucose(glucose: Glucose) {
        coroutineScope.launch {
            database.glucoseDao().updateGlucose(glucose)
        }
    }

    suspend fun addGlucose(glucose: Glucose) {
        database.glucoseDao().addGlucose(glucose)
    }

    suspend fun deleteGlucose(glucoseDate: Date) {
        database.glucoseDao().deleteGlucose(glucoseDate)
    }

    companion object {
        private var INSTANCE: GlucoseRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = GlucoseRepository(context)
            }
        }

        fun get(): GlucoseRepository {
            return INSTANCE ?:
            throw IllegalStateException("CrimeRepository must be initialized")
        }
    }
}