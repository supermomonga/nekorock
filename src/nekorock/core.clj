(ns nekorock.core
  (:require [clojure.core.typed :as t]
            [clj-http.client :as c]
            [nekorock.imas :as imas]
            [environ.core :refer [env] :as e])
  (:gen-class))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [cs (clj-http.cookies/cookie-store)]
    (println
     (imas/login cs (env :mobage-username) (env :mobage-password) (env :2captcha-api-key))))
  (println "oh,"))


