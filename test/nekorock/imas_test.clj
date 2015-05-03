(ns nekorock.imas-test
  (:require [clojure.test :refer :all]
            [nekorock.imas :refer :all :as imas]
            [environ.core :refer [env] :as e]))

(def cs (clj-http.cookies/cookie-store))

(deftest mobage-test
  (testing "Mobamas client"
    (testing "can get mobage-username from environment variables"
      (is (re-find #".+@.+" (env :mobage-username))))
    (testing "can login to the mobage"
      (is (re-find #"\d+" (imas/login cs
                                      (env :mobage-username)
                                      (env :mobage-password)
                                      (env :2captcha-api-key)))))
    (testing "can create mobage server proxied url"
      (is (= "hi" (imas/generate-imas-url "results/index/"))))))
