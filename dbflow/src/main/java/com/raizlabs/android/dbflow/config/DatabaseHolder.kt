package com.raizlabs.android.dbflow.config

import com.raizlabs.android.dbflow.converter.TypeConverter
import java.util.*

/**
 * Description: The base interface for interacting with all of the database and top-level data that's shared
 * between them.
 */
abstract class DatabaseHolder {

    val databaseDefinitionMap: MutableMap<Class<*>, DatabaseDefinition> = HashMap()
    val databaseNameMap: MutableMap<String, DatabaseDefinition> = HashMap()
    val databaseClassLookupMap: MutableMap<Class<*>, DatabaseDefinition> = HashMap()
    val typeConverters: MutableMap<Class<*>, TypeConverter<*, *>> = HashMap()

    val databaseDefinitions: List<DatabaseDefinition>
        get() = ArrayList(databaseNameMap.values)

    /**
     * @param clazz The model value class to get a [com.raizlabs.android.dbflow.converter.TypeConverter]
     * @return Type converter for the specified model value.
     */
    fun getTypeConverterForClass(clazz: Class<*>): TypeConverter<*, *>? {
        return typeConverters[clazz]
    }

    /**
     * @param table The model class
     * @return The database that the table belongs in
     */
    fun getDatabaseForTable(table: Class<*>): DatabaseDefinition? {
        return databaseDefinitionMap[table]
    }

    fun getDatabase(databaseClass: Class<*>): DatabaseDefinition? {
        return databaseClassLookupMap[databaseClass]
    }

    /**
     * @param databaseName The name of the database to retrieve
     * @return The database that has the specified name
     */
    fun getDatabase(databaseName: String): DatabaseDefinition? {
        return databaseNameMap[databaseName]
    }

    /**
     * Helper method used to store a database for the specified table.
     *
     * @param table              The model table
     * @param databaseDefinition The database definition
     */
    fun putDatabaseForTable(table: Class<*>, databaseDefinition: DatabaseDefinition) {
        databaseDefinitionMap.put(table, databaseDefinition)
        databaseNameMap.put(databaseDefinition.databaseName, databaseDefinition)
        databaseClassLookupMap.put(databaseDefinition.associatedDatabaseClassFile, databaseDefinition)
    }

    fun reset() {
        databaseDefinitionMap.clear()
        databaseNameMap.clear()
        databaseClassLookupMap.clear()
        typeConverters.clear()
    }
}
