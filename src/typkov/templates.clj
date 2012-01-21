(ns typkov.templates
  (:use [noir.core :only [defpartial]]
        [hiccup.page-helpers :only [include-css html5 include-js]]))

(defpartial base [title & content]
  (html5
    [:head
     [:title title]
     (include-css "/css/bootstrap.css")
     [:link {:rel "stylesheet/less" :type "text/css" :href "/css/style.less"}]
     (include-js "/js/less.js")]
    [:body
     [:div#wrapper
      content]]))


(defpartial home []
  (base "Typkov"))
