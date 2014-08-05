# Food Hygiene Rating


Do you know that sticker on the window of most takeaway shops? that sticker displays the Hygiene rating. This post is not about those stickers.

This project contains the code to download and analyse the FHR dataset. I've included the aggregation queries for ElasticSearch also.

### The Dataset

FHR has the data spread around 500 xml files. The Java project loads, reads, parses and extracts the Establishments data. A word of caution, the dataset contains duplicated entries in English and Welsh. This code filters out the Welsh data.

This is what an Establishment looks like:

    <EstablishmentDetail>
        <FHRSID>40772</FHRSID>
        <LocalAuthorityBusinessID>101660</LocalAuthorityBusinessID>
        <BusinessName>Zeera Indian Takeaway</BusinessName>
        <BusinessType>Takeaway/sandwich shop</BusinessType>
        <BusinessTypeID>7844</BusinessTypeID>
        <AddressLine1>16 Victoria Terrace,</AddressLine1>
        <AddressLine2>Trecelyn,</AddressLine2>
        <AddressLine3>Casnewydd</AddressLine3>
        <AddressLine4>Newport</AddressLine4>
        <PostCode>NP11 4ET</PostCode>
        <RatingValue>3</RatingValue>
        <RatingKey>fhrs_3_cy-GB</RatingKey>
        <RatingDate>2014-02-10</RatingDate>
        <LocalAuthorityCode>555</LocalAuthorityCode>
        <LocalAuthorityName>Caerffili</LocalAuthorityName>
        <LocalAuthorityWebSite>http://www.caerphilly.gov.uk</LocalAuthorityWebSite>
        <LocalAuthorityEmailAddress>foodhealthandsafety@caerphilly.gov.uk</LocalAuthorityEmailAddress>
        <Scores>
            <Hygiene>10</Hygiene>
            <Structural>10</Structural>
            <ConfidenceInManagement>10</ConfidenceInManagement>
        </Scores>
        <SchemeType>FHRS</SchemeType>
        <Geocode>
            <Longitude>-3.14466500000000</Longitude>
            <Latitude>51.66539100000000</Latitude>
        </Geocode>
    </EstablishmentDetail>

Note that we have longitude and latitude, in case you want map the details.

Creating the ElasticSearch Index is not complicated:

    curl -XPUT 'localhost:9200/fhr' -d '{
     "settings" : {
         "index" : {
            "number_of_shards" : 1,
            "number_of_replicas" : 0
        }
     },
    "mappings": {
        "establishment": {
            "properties": {
                "FHRSID": {
                    "type": "long"
                },
                "LocalAuthorityBusinessID": {
                    "type": "long"
                },
                "BusinessName": {
                    "type": "string"
                },
                "BusinessType": {
                    "type": "string"
                },
                "BusinessTypeID": {
                    "type": "long"
                },
                "AddressLine1": {
                    "type": "string"
                },
                "AddressLine2": {
                    "type": "string"
                },
                "AddressLine3": {
                    "type": "string"
                },
                "AddressLine4": {
                    "type": "string"
                },
                "PostCode": {
                    "type": "string"
                },
                "outward": {
                    "type": "string"
                },
                "areacode": {
                    "type": "string"
                },
                "RatingValue": {
                    "type": "integer"
                },
                "RatingKey": {
                    "type": "string"
                },
                "RatingDate": {
                    "type": "date",
                    "format": "dateOptionalTime"
                },
                "LocalAuthorityCode": {
                    "type": "long"
                },
                "LocalAuthorityName": {
                    "type": "string"
                },
                "LocalAuthorityWebSite": {
                    "type": "string"
                },
                "LocalAuthorityEmailAddress": {
                    "type": "string"
                },
                "Scores":{
                    "type" : "object",
                    "properties" : {
                        "Hygiene": {
                            "type": "integer"
                        },
                        "Structural": {
                            "type": "integer"
                        },
                        "ConfidenceInManagement": {
                            "type": "integer"
                        }
                    }
                },
                "Geocode":{
                       "type" : "geo_point"
                }
            }
        }
    }
    }'


The Java code is completed, the python is work in progress and it only downloads the dataset.

If you run the me.pedrazas.fhr.LanuchCrawler class it will do everything for you. Once you have the data inserted into ElasticSearch you can start quering the index.

## ElasticSearch Queries

### Aggregate Ratings by PostCode starting by 'PO18'

    GET _search
    {

       "query" : {
          "match" : { "PostCode" : "PO18*"}
           },
       "aggs" : {
            "Food_hygiene_ratings" : {
               "terms" : {
                 "field" : "RatingValue"
               }
            }
          }
    }

You can look at the results [in this file](results/result-query1.json). The aggregations are at the bottom:

    "aggregations": {
          "Food_hygiene_ratings": {
             "buckets": [
                {
                   "key": 5,
                   "key_as_string": "5",
                   "doc_count": 93
                },
                {
                   "key": 4,
                   "key_as_string": "4",
                   "doc_count": 27
                },
                {
                   "key": 0,
                   "key_as_string": "0",
                   "doc_count": 12
                },
                {
                   "key": 3,
                   "key_as_string": "3",
                   "doc_count": 5
                },
                {
                   "key": 2,
                   "key_as_string": "2",
                   "doc_count": 2
                },
                {
                   "key": 1,
                   "key_as_string": "1",
                   "doc_count": 1
                }
             ]
          }
       }

What about the average?

    GET _search
    {

       "query" : {
          "match" : { "PostCode" : "PO18*"}
           },
       "aggs" : {
            "Food_hygiene_ratings" : {
               "terms" : {
                 "field" : "RatingValue"
               }
            },

            "avg_rating" : {
              "avg" : {
                "field" : "RatingValue" }

            }
          }
    }

Results [in this other file](results/result-query2.json). As you can see, the only difference is this entry:

       "aggregations": {
          "avg_rating": {
             "value": 4.235714285714286
          },

This is fine, but what about getting all the ratings for the postcodes? the catch here is to realise that we need either the area code (PO) or the outward (PO18) of the postcode. The following query returns the **top** results:

    GET _search
    {

       "aggs" : {
            "Food_hygiene_ratings" : {
               "terms" : {
                 "field" : "outward"
               },
               "aggs":{
                 "postcodes":{
                 "terms" : {
                   "field" : "RatingValue"
                  }
                 }
                 ,

            "avg_height" : {
              "avg" : {
                "field" : "RatingValue" }

            }
               }
            }
          }
    }

If you want to get all the results, this is the query you need:

    GET _search
    {
        "size": 0,
       "aggs" : {
            "Food_hygiene_ratings" : {
               "terms" : {
                 "field" : "outward",
                 "size": 0
               },
               "aggs":{
                 "postcodes":{
                 "terms" : {
                   "field" : "RatingValue"
                  }
                 }
                 ,

            "avg_height" : {
              "avg" : {
                "field" : "RatingValue" }

            }
               }
            }
          }
    }

Results [in this file](results/result-query3.json). Note that this quite a big file but if you want to create a map per postcode areas, this is your datasource. To be able to find the cleanest post code area, I followed a different technique. First I query per areacode, extracted the best and the worst, and issued another query to get more details of that area.

Why? well, when analysing data you have to make certain asumptions. For me, this one made more sense than getting the aggregations of all the outwards (it helps to reduce rubbish data).

    GET _search
    {

       "aggs" : {
            "Food_hygiene_ratings" : {
               "terms" : {
                 "field" : "areacode",
                 "size": 0,
                 "order": {
                        "avg_height": "desc"
                    }

               },

               "aggs":{
                 "postcodes":{
                 "terms" : {
                   "field" : "RatingValue"
                  }
                 }
                 ,

            "avg_height" : {
              "avg" : {
                "field" : "RatingValue"

              }

            }
               }
            }
          }
    }


Results [in this file](results/result-query4.json). And from the results I went to get the cleanest (TAUNTON  -- TA3) and the filthiest (HAWICK, NEWCASTLETON -- TD9) using this query:

    GET _search
    {

        "query" : {
          "match" : { "areacode" : "td"}
           },
       "aggs" : {
            "Food_hygiene_ratings" : {
               "terms" : {
                 "field" : "outward",
                 "size": 0,
                 "order": {
                        "avg_height": "desc"
                    }

               },

               "aggs":{

                  "avg_height" : {
                    "avg" : {
                      "field" : "RatingValue"
                    }

                  }
               }
          }
        }
    }