(ns nekorock.core
  (:require [clojure.core.typed :as t]
            [clj-http.client :as c]
            [nekorock.imas :as imas])
  (:gen-class))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [cs (clj-http.cookies/cookie-store)]
    (println
     (imas/login cs (System/getenv "MOBAGE_USERNAME") (System/getenv "MOBAGE_PASSWORD"))))
  (println "Hello, World!")
  (println "oh,"))


