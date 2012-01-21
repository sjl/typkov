(ns typkov.views.core
  (:require [typkov.templates :as t])
  (:require [noir.validation :as valid])
  (:use [noir.core :only [render defpage]]))

(defn text-valid? [{:keys [text]}]
  (valid/rule (valid/min-length? text 24)
              [:text "You must enter some text!"])
  (not (valid/errors? :text)))

(defpage "/" {:as data}
  (t/home (:text data) nil))

(defpage [:post "/"] {:as data}
  (if (text-valid? data)
    (t/home (:text data) "some lesson here")
    (render "/" data)))
