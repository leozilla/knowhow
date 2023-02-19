# BigQuery

features:
- clustered tables
- partitioned tables (partition by date is recommended to limit the partitions which get scannend when querying by time)
- materilize query results of stages in multi stage queries (set short retention time for this materilized tables when possible)
- loading data into BigQuery is free (but streaming data into BigQuery is NOT)
- external data sources are supported for querying data that does not live in BigQuery
- different kinds of queries
    - interactive queries and views
    - batch queries
    - partitioned table queries to query only a subset of data
    - all potentially long running actions are modeled as jobs (which can be polled for their status)
- table schema
    - Avro is the preferred format for loading data into BigQuery
    - schema autodetection (eg via Avro)
    - When you load Avro the schema is automatically retrieved from the self-describing source data.
    - columns have an optional description
    - struct/recrod type with nested and repeated columns (for 1-N and even deeper realtions)
    - modifying nested and repeated columns
    - specifying schema via JSON schema file
- table export
    - You can use a service such as Dataflow to read data from BigQuery instead of manually exporting it


Attention:
- streaming data into BigQuery can get expensive
- dataset limitations
    - dataset location is immutable, cant be moved once created but it can be copied (expensive!)
    - All tables that are referenced in a query must be stored in datasets in the same location.
    - When you copy a table, the datasets that contain the source table and destination table must reside in the same location.
- location considerations
    - Colocate your BigQuery dataset and your external data source.
    - Colocate your Cloud Storage buckets for loading or exporting data.
- table schema
    - Duplicate column names are not allowed even if the case differs
    - unsupported schema modifications which require manual workarounds include the following:
        - Changing a column's name
        - Changing a column's data type
        - Changing a column's mode (aside from relaxing REQUIRED columns to NULLABLE)
        - Deleting a column.
- table limitations
    - When copy multiple source tables to a destination table, all source tables must have identical schemas
    - When you export table data, the only supported destination is Cloud Storage.
    - enumeration performance slows as you approach 50,000 tables in a dataset.
    - copying tables
        - time vary significantly across different runs because the underlying storage is managed dynamically.
    - exporting
        - BigQuery can export up to 1 GB of data to a single file  