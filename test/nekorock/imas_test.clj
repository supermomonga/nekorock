(ns nekorock.imas-test
  (:require [clojure.test :refer :all]
            [nekorock.imas :refer :all :as imas]
            [environ.core :refer [env] :as e]))

(def cs (clj-http.cookies/cookie-store))

(deftest mobage-client-test
  (testing "Mobamas client"
    (testing "can get mobage-username from environment variables"
      (is (re-find #".+@.+" (env :mobage-username))))
    (testing "can login to the mobage"
      (is (re-find #"\d+" (imas/login cs
                                      (env :mobage-username)
                                      (env :mobage-password)
                                      (env :2captcha-api-key)))))))

(deftest mobage-url-test
  (testing "Can create imas url"
    (is (=
         (imas/generate-imas-url "results/index")
         "http://125.6.169.35/idolmaster/results/index"))
    (is (=
         (imas/generate-imas-url "results/index" {:hoge "moge"})
         "http://125.6.169.35/idolmaster/results/index?hoge=moge"))
  (testing "Can create mobage server proxied url"
    (is (=
         (imas/generate-proxied-url (imas/generate-imas-url "results/index" {:l_frm "Mypage_1"}))
         "http://sp.pf.mbga.jp/12008305/?url=http%3A%2F%2F125.6.169.35%2Fidolmaster%2Fresults%2Findex%3Fl_frm%3DMypage_1"))
    (is (=
         (imas/generate-proxied-url (imas/generate-imas-url "auction/auction_top" {:l_frm "Results_1"}))
         "http://sp.pf.mbga.jp/12008305/?url=http%3A%2F%2F125.6.169.35%2Fidolmaster%2Fauction%2Fauction_top%3Fl_frm%3DResults_1"))
    (is (=
         (imas/url "results/index" {:l_frm "Mypage_1"})
         "http://sp.pf.mbga.jp/12008305/?url=http%3A%2F%2F125.6.169.35%2Fidolmaster%2Fresults%2Findex%3Fl_frm%3DMypage_1"))
    (is (=
         (imas/url "auction/auction_top" {:l_frm "Results_1"})
         "http://sp.pf.mbga.jp/12008305/?url=http%3A%2F%2F125.6.169.35%2Fidolmaster%2Fauction%2Fauction_top%3Fl_frm%3DResults_1")))))
