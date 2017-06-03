# source-search

###A basic application to replicate searching within source files.

#### Features and points to be noted to use the application:

* Application is configuration driven
* In its basic form it runs on localhost spray-can server at 8080 port configured in `application.conf` file
* For the sake of simplicity the application only supports 
    * Searching strings with alphabetical content **_only_** 
        * For eg: a string such as `A12BC=` is not supported in search query
    *  **_case insensitive_** search 
    * Full text search **_only_**.
* At the start of the application source files are picked from the `application.conf`
 which are strings comma separated under the configuration key `source-files`.
    
    * Very strict error handling is not done to verify if files mentioned in configuration.
    So make sure valid files are kept.
* Source files are indexed in redis server running for quick search retrieval.
    * Redis related configuration are to be entered in `application.conf`
* Finally to use and search for words in indexed source 
files use a url pattern like : 
```http://localhost:8080/search?words=<your search phrase here separated by space>``` 