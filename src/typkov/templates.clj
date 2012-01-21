(ns typkov.templates
  (:require [noir.validation :as valid])
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

(defpartial error-item [[first-error]]
  [:div.alert-message.error first-error])

(defpartial home [text lesson]
  (base "Typkov"
        [:p "Enter some text and we'll create a gtypist lesson from it for you."]
        [:form {:method "post" :action "" :class "form-stacked"}
         [:fieldset.text
          [:label {:for "text"}
           "Text"]
          (valid/on-error :text error-item)
          [:textarea#text.error {:name "text"} text]]
         [:button {:type "submit" :class "btn primary"} "Get a Lesson"]]
        (when lesson
          [:textarea.lesson lesson])))
