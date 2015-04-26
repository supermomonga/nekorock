(ns nekorock.imas
  (:require [clojure.core.typed :as t]
            [clj-http.client :as c])
  (:gen-class))

(def h {"User-Agent" "Mozilla/5.0 (Linux; U; Android 4.0.1; ja-jp; Galaxy Nexus Build/ITL41D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30"})

(t/ann login [BasicCookieStore, String, String -> String])
(defn login [cs username password]
  "Login to mobage"
  (println username)
  (-> (c/get "https://ssl.sp.mbga.jp/_lg" {:headers h :cookie-store cs})
      :orig-content-encoding))
