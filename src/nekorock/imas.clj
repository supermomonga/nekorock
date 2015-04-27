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
  (let [params (-> (c/get "https://connect.mobage.jp/login" {:headers h :cookie-store cs})
                   :body
                   (e/html-snippet)
                   (as-> it
                       (hash-map :login_history_id
                                 (-> (e/select it [[(e/attr= :name "login_history_id")]]) first :attrs :value)
                                 :post_login_redirect_uri
                                 (-> (e/select it [[(e/attr= :name "post_login_redirect_uri")]]) first :attrs :value)
                                 :captcha_state
                                 (-> (e/select it [[(e/attr= :name "captcha_state")]]) first :attrs :value)
                                 :captcha_url
                                 (-> (e/select it [[:img (e/attr-starts :src "/captcha/image?captcha_state=")]]) first :attrs :src
                                     (#(str "https://connect.mobage.jp" %)))
                                 :csrf_token
                                 (-> (e/select it [:meta#mobage-connect-app-csrf-token]) first :attrs :content)
                                 :client_id (-> (e/select it [[(e/attr= :name "client_id")]]) first :attrs :value)
                                 :theme (-> (e/select it [[(e/attr= :name "theme")]]) first :attrs :value)
                                 :display (-> (e/select it [[(e/attr= :name "display")]]) first :attrs :value)
                                 :width (-> (e/select it [[(e/attr= :name "width")]]) first :attrs :value)
                                 :height (-> (e/select it [[(e/attr= :name "height")]]) first :attrs :value)
                                 :enable_federation (-> (e/select it [[(e/attr= :name "enable_federation")]]) first :attrs :value)
                                 ))
                   (into {:subject_id username
                          :subject_password password})
                   (as-> it
                       (into {} (for [[k v] it]
                                  [(name k) v]))))]
    (println params)
    (println "CAPTCHA url is:" (params "captcha_url"))
    (c/post "https://connect.mobage.jp/login" {:headers h
                                               :cookie-store cs
                                               :client-params params})
    )
  "hi")

(defn logout [cs username password]
  "Logout from mobage"
  (-> (c/get "http://sp.mbga.jp/_logout_confirm" {:headers h :cookie-store cs})))

;; https://connect.mobage.jp/login?post_login_redirect_uri=https%3A%2F%2Fconnect.mobage.jp%2Fconnect%2F1.0%2Fservices%2Fauthorize%3Fresponse_type%3Dcode%26client_id%3Dportal-4%26scope%3Dopenid%26redirect_uri%3Dhttps%253A%252F%252Fssl.sp.mbga.jp%252F_connect_login_callback%26state%3D1397494.UcH63tDI990jxtrjNKUxKIwf.1430163291%26display%3Dtouch%26prompt%3Dconsent&sig=3ea53947c5c02b8b18ee6e084c0d5e13af51df73&iat=1430163291&seed=1s6qHybMAyw&display=touch
;; https://connect.mobage.jp/login?post_login_redirect_uri=https://connect.mobage.jp/connect/1.0/services/authorize?response_type=code&client_id=portal-4&scope=openid&redirect_uri=https%3A%2F%2Fssl.sp.mbga.jp%2F_connect_login_callback&state=1397494.UcH63tDI990jxtrjNKUxKIwf.1430163291&display=touch&prompt=consent&sig=3ea53947c5c02b8b18ee6e084c0d5e13af51df73&iat=1430163291&seed=1s6qHybMAyw&display=touch
;; https://connect.mobage.jp/login?post_login_redirect_uri=https://connect.mobage.jp/connect/1.0/services/authorize?response_type=code&client_id=portal-4&scope=openid&redirect_uri=https://ssl.sp.mbga.jp/_connect_login_callback&state=1397494.UcH63tDI990jxtrjNKUxKIwf.1430163291&display=touch&prompt=consent&sig=3ea53947c5c02b8b18ee6e084c0d5e13af51df73&iat=1430163291&seed=1s6qHybMAyw&display=touch
