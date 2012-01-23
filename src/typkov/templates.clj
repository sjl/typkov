(ns typkov.templates
  (:require [noir.validation :as valid])
  (:use [noir.core :only [defpartial]]
        [hiccup.form-helpers :only [hidden-field]]
        [hiccup.page-helpers :only [include-css html5 include-js link-to]]))


; Utils -----------------------------------------------------------------------
(def gauges "
  <script type='text/javascript'>
    var _gauges = _gauges || [];
    (function() {
      var t   = document.createElement('script');
      t.type  = 'text/javascript';
      t.async = true;
      t.id    = 'gauges-tracker';
      t.setAttribute('data-site-id', '4f1b29ce613f5d7d9b000001');
      t.src = '//secure.gaug.es/track.js';
      var s = document.getElementsByTagName('script')[0];
      s.parentNode.insertBefore(t, s);
    })();
  </script>")

(def rochester-made "
<a id='rochester-made' href='http://rochestermade.com' title='Rochester Made'><img src='http://rochestermade.com/media/images/rochester-made-dark-on-light.png' alt='Rochester Made' title='Rochester Made' /></a>
  ")


(defpartial select [id name options selected]
  [:select {:name name :id id}
   (map (fn [o]
          [:option (merge {:value o}
                          (when (= selected o) {:selected "selected"}))
           o])
        options)])

(defpartial error-item [[first-error]]
  [:div.alert-message.error first-error])


; Base ------------------------------------------------------------------------
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
       [:h1 (link-to "/" "Typkov")]
       [:h2
        "Create typing lessons from your own writing!"]]
      content
      [:footer
       rochester-made
       [:p
        "Created by "
        (link-to "http://stevelosh.com/" "Steve Losh")
        "."
        [:p
         "It's open source"
         [:br]
         (link-to "http://bitbucket.org/sjl/typkov" "on BitBucket")
         " and "
         (link-to "http://github.com/sjl/typkov" "on GitHub")
         "."]]]
      gauges]]))


; Home ------------------------------------------------------------------------
(defpartial main-copy []
  (list
    [:p
     "Typing practice is great, but wouldn't it be wonderful to "
     "practice with the kind of words you usually use in your own writing?"]
    [:p
     "Enter some text and we'll use it to create a "
     (link-to "http://www.gnu.org/software/gtypist/" "gtypist")
     " or "
     (link-to "http://ktouch.sourceforge.net/" "ktouch")
     " lesson file for you."]))

(defpartial home-form [text num format]
  (list
    (main-copy)
    [:form {:method "post" :action "" :class "form-stacked"}
     [:fieldset.text
      [:label {:for "text"}
       "Paste in some text you've written:"]
      (valid/on-error :text error-item)
      [:textarea#text {:name "text"} text]]
     [:fieldset.format
      [:label {:for "format"}
       "Which typing program do you use?"]
      (valid/on-error :format error-item)
      (select "format" "format" ["gtypist" "ktouch"] format)]
     [:fieldset.num
      [:label {:for "num"}
       "How many drills do you want?"]
      (valid/on-error :num error-item)
      (select "num" "num" (range 10 51 10) num)]
     [:button {:type "submit" :class "btn primary"} "Get a Lesson"]]))


(defpartial usage-gtypist []
  [:p
   "Copy all of the following into a "
   [:code "whatever.typ"]
   " file and run "
   [:code "gtypist whatever.typ"]
   " to use it!"])

(defpartial usage-ktouch []
  [:p
   "Copy all of the following into a "
   [:code "whatever.ktouch"]
   " file and load it up in ktouch to use it!"])

(defpartial home-lesson [text num format lesson]
  (list
    (case format
      "gtypist" (usage-gtypist)
      "ktouch" (usage-ktouch))
    [:form.noop
     [:textarea.lesson lesson]]
    [:form {:method "post" :action ""}
     [:button.btn {:type "submit"} "Start Over"]
     (hidden-field "restart" "1")
     (hidden-field "text" text)
     (hidden-field "num" num)
     (hidden-field "format" format)]))


(defpartial home [text num format lesson]
  (base "Typkov"
        (if lesson
          (home-lesson text num format lesson)
          (home-form text num format))))
