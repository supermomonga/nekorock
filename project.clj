(defproject nekorock "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/core.typed "0.2.84"]
                 [environ "1.0.0"]
                 [clj-http "1.1.1"]
                 [enlive "1.1.5"]]
  :plugins [[lein-environ "1.0.0"]]
  :main ^:skip-aot nekorock.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
