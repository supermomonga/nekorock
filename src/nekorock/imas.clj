(ns nekorock.imas
  (:require [clojure.core.typed :as t]
            [clj-http.client :as c]
            [net.cgrand.enlive-html :as e])
  (:gen-class))

(def h {"User-Agent" "Mozilla/5.0 (Linux; U; Android 4.0.1; ja-jp; Galaxy Nexus Build/ITL41D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30"})

(t/ann login [BasicCookieStore, String, String -> String])
(defn login [cs username password]
  "Login to mobage"
  (println username)
  (-> (c/get "https://ssl.sp.mbga.jp/_lg" {:headers h :cookie-store cs})
      :body
      (java.io.StringReader.)
      (e/html-resource)
      (as-> it
          (hash-map :login_history_id (-> (e/select it [[:input (e/attr= :name "login_history_id")]]) first :attrs :value)
                    :post_login_redirect_uri (-> (e/select it [[:input (e/attr= :name "post_login_redirect_uri")]]) first :attrs :value)))
      (println)
      (str "hi")))

