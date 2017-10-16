package uk.panasys.phonehabits.database

import android.content.Context
import com.couchbase.lite.Database
import com.couchbase.lite.DatabaseOptions
import com.couchbase.lite.Document
import com.couchbase.lite.Manager
import com.couchbase.lite.android.AndroidContext
import java.util.*


class DatabaseRepo(appContext: Context, dbName: String) {

    private lateinit var manager: Manager
    private lateinit var database: Database

    init {
        val options = DatabaseOptions()
        options.isCreate = true
        this.manager = Manager(AndroidContext(appContext), Manager.DEFAULT_OPTIONS)
        this.database = this.manager.openDatabase(dbName, options)
    }

    fun insertDocument(documentMap: MutableMap<String?, Any?>): String {
        val docId = UUID.randomUUID().toString()
        val document = database.getDocument(docId)
        document.putProperties(documentMap)
        return docId
    }

    fun getDocument(docId: String): Document? {
        return database.getDocument(docId)
    }

}