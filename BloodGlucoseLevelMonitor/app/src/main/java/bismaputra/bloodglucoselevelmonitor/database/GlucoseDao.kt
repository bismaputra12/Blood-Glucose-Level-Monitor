package bismaputra.bloodglucoselevelmonitor.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import bismaputra.bloodglucoselevelmonitor.Glucose
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface GlucoseDao {
    @Query("SELECT * FROM Glucose")
    fun getGlucoses(): Flow<List<Glucose>>

    @Query("SELECT * FROM Glucose WHERE date=(:glucoseDate)")
    fun getGlucose(glucoseDate: Date): Glucose

    @Update
    suspend fun updateGlucose(glucose: Glucose)
}