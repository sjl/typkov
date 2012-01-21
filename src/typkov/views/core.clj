(ns typkov.views.core
  (:require [typkov.templates :as t])
  (:use [noir.core :only [defpage]]))


(defpage "/" []
  (t/home))

