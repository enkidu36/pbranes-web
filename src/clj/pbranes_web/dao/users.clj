(ns pbranes-web.dao.users
                                               (:import java.sql.Timestamp
                                                        java.util.Date
                                                        [java.sql
                                                         BatchUpdateException
                                                         PreparedStatement])
                                               (:require [pbranes-web.db.core :refer :all]
                                                         [clojure.java.jdbc :as sql]
                                                         [hugsql.core :as hugsql]
                                                         [clojure.java.jdbc :as jdbc]))



                  ;(delete-user! {:id "2"})

                  (def create-data {:id "3"
                                    :first_name "Joe"
                                    :last_name "Savoie"
                                    :email "enkidu36@yahoo.com"
                  :admin true
                  ;:last_login (java.util.Date.)
                  :is_active true
                  :pass "test"})

;(create-user! create-data)

