import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DBHelper(val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "mydatabase.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_NAME = "location_items"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_PROVINCE = "province"
        private const val COLUMN_IMG = "img"
        private const val COLUMN_RATING = "rating"

        private const val SQL_CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME (" +
                    "$COLUMN_ID TEXT PRIMARY KEY," +
                    "$COLUMN_NAME TEXT," +
                    "$COLUMN_PROVINCE TEXT," +
                    "$COLUMN_IMG TEXT," +
                    "$COLUMN_RATING REAL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database upgrades (if needed)
    }

    fun insertLocation(locationItem: LocationItem) {
        val db = writableDatabase

        // Check if the table exists
        if (isTableExists(db, TABLE_NAME)) {
            // Table exists, proceed with insertion
            val values = ContentValues().apply {
                put(COLUMN_ID, locationItem.id)
                put(COLUMN_NAME, locationItem.name)
                put(COLUMN_PROVINCE, locationItem.province)
                put(COLUMN_IMG, locationItem.img)
                put(COLUMN_RATING, locationItem.rating)
            }

            val newRowId = db.insert(TABLE_NAME, null, values)

            if (newRowId != -1L) {
                //Toast.makeText(context, "LocationItem inserted with ID: $newRowId", Toast.LENGTH_SHORT).show()
            } else {
               // Toast.makeText(context, "Error inserting LocationItem", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Table does not exist, handle accordingly
           // Toast.makeText(context, "Table $TABLE_NAME does not exist", Toast.LENGTH_SHORT).show()
        }

        db.close()
    }

    // Function to check if a table exists in the database
    private fun isTableExists(db: SQLiteDatabase, tableName: String): Boolean {
        val query = "SELECT name FROM sqlite_master WHERE type='table' AND name='$tableName'"
        val cursor = db.rawQuery(query, null)
        val tableExists = cursor.moveToFirst()
        cursor.close()
        return tableExists
    }

    fun queryLocations(): List<LocationItem> {
        val db = readableDatabase

        val projection = arrayOf(
            COLUMN_ID,
            COLUMN_NAME,
            COLUMN_PROVINCE,
            COLUMN_IMG,
            COLUMN_RATING
        )

        val cursor: Cursor = db.query(
            TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )

        val locationItems = mutableListOf<LocationItem>()

        while (cursor.moveToNext()) {
            val itemId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val itemName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val itemProvince = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PROVINCE))
            val itemImg = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMG))
            val itemRating = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_RATING))

            val locationItem = LocationItem(itemId, itemName, itemProvince, itemImg, itemRating)
            locationItems.add(locationItem)
        }

        cursor.close()
        db.close()

        return locationItems
    }

    fun clearAllLocations() {
        val db = writableDatabase

        // Delete all rows from the table
        db.delete(TABLE_NAME, null, null)

        Toast.makeText(context, "All data cleared from the table", Toast.LENGTH_SHORT).show()

        db.close()
    }

    // Add this method to your DBHelper
    fun getLocationIdByName(name: String): String? {
        val db = readableDatabase
        val selection = "$COLUMN_NAME = ?"
        val selectionArgs = arrayOf(name)

        val projection = arrayOf(COLUMN_ID)

        val cursor: Cursor = db.query(
            TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        var locationId: String? = null

        if (cursor.moveToFirst()) {
            locationId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID))
        }

        cursor.close()
        db.close()

        return locationId
    }

}
