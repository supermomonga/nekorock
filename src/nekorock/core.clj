(ns nekorock.core
  (:require [clojure.core.typed :as t]
            [clj-http.client :as c])
  (:gen-class))


(def h {"User-Agent"
        "Mozilla/5.0 (Linux; U; Android 4.0.1; ja-jp; Galaxy Nexus Build/ITL41D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30"})

(t/ann login [String, String -> String])
(defn login [username password]
  "Login to mobage"
  (println username)
  (-> (c/get "https://ssl.sp.mbga.jp/_lg" {:headers h})
      :orig-content-encoding))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println
   (login (System/getenv "MOBAGE_USERNAME") (System/getenv "MOBAGE_PASSWORD")))
  (println "Hello, World!")
  (println "oh,"))


