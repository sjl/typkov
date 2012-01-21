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
       [:h2
        "Create "
        (link-to "http://www.gnu.org/software/gtypist/" "gtypist")
        " lessons from your own writing!"]]
      content
      [:footer
       [:p
        "Created by "
        (link-to "http://stevelosh.com/" "Steve Losh")
        "."
        [:p
        "It's open source "
        (link-to "http://bitbucket.org/sjl/typkov" "on BitBucket")
        " and "
        (link-to "http://github.com/sjl/typkov" "on GitHub")
        "."]]]]]))


(defpartial error-item [[first-error]]
  [:div.alert-message.error first-error])


(defpartial home [text lesson]
  (base "Typkov"
        [:p
         "Typing practice is great, but wouldn't it be wonderful to "
         "practice with the kind of words you usually use in your own writing?"]
        [:p
         "Enter some text and we'll create a "
         (link-to "http://www.gnu.org/software/gtypist/" "gtypist")
         " lesson based on it for you."]
        [:form {:method "post" :action "" :class "form-stacked"}
         [:fieldset.text
          [:label {:for "text"}
           "Paste in some text you've written:"]
          (valid/on-error :text error-item)
          [:textarea#text.error {:name "text"} text]]
         [:button {:type "submit" :class "btn primary"} "Get a Lesson"]]
        (when lesson
          (list
            [:p
             "Copy all of the following into a "
             [:code "whatever.typ"]
             " file and use "
             [:code "gtypist whatever.typ"]
             " to use it!"]
            [:textarea.lesson lesson]))))
