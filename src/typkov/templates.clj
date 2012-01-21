(ns typkov.templates
  (:use [noir.core :only [defpartial]]
        [hiccup.page-helpers :only [include-css html5 include-js link-to]]))

(defpartial base [title & content]
  (html5
    [:head
     [:title title]
     (include-css "/css/bootstrap.css")
     [:link {:rel "stylesheet/less" :type "text/css" :href "/css/style.less"}]
     (include-js "/js/less.js")]
    [:body
     [:div#content
      [:header
       [:h1 "Typkov"]
       [:h2 "Create gtypist lessons from your own writing!"]]
      content
      [:footer
       [:p
        "Created by "
        (link-to "http://stevelosh.com/" "Steve Losh")
        "."]]]]))


(defpartial home []
  (base "Typkov"
        [:p "Enter some text and we'll create a gtypist lesson from it for you."]
        [:form {:method "post" :action "" :class "form-stacked"}
         [:fieldset.text
          [:label {:for "text"}
           "Text"]
          [:textarea#text {:name "text"}]]
         [:button {:type "submit" :class "btn primary"} "Get a Lesson"]]))
