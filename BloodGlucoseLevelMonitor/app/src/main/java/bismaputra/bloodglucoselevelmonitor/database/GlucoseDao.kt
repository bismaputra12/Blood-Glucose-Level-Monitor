package bismaputra.bloodglucoselevelmonitor.database

import androidx.room.*
import bismaputra.bloodglucoselevelmonitor.Glucose
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface GlucoseDao {
    @Query("SELECT * FROM Glucose")
    fun getGlucoses(): Flow<List<Glucose>>

    @Query("SELECT * FROM Glucose WHERE date=(:glucoseDate)")
    suspend fun getGlucose(glucoseDate: Date): Glucose

    @Update
    suspend fun updateGlucose(glucose: Glucose)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGlucose(glucose: Glucose)
}