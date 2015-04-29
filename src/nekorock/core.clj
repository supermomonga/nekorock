(ns nekorock.core
  (:require [clojure.core.typed :as t]
            [clj-http.client :as c]
            [nekorock.imas :as imas]
            [environ.core :refer [env] :as e])
  (:gen-class))


(defn -main
  "crawling trade log"
  [& args]
  (let [cs (clj-http.cookies/cookie-store)]
    (if-let [mobage-id
               (imas/login
                cs
                (env :mobage-username)
                (env :mobage-password)
                (env :2captcha-api-key))]
      (println "Logined. mobage id:" mobage-id)
      (println "Login failed."))))


