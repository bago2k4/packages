(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[cljsjs/boot-cljsjs "0.5.2" :scope "test"]
                  [cljsjs/jquery    "2.2.4-0"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +lib-version+ "1.7.3")
(def +version+ (str +lib-version+ "-0"))

(task-options!
  pom  {:project     'cljsjs/jquery-textcomplete
        :version     +version+
        :description "Introduce autocompleting power to textareas, like GitHub comment forms have."
        :url         "http://yuku-t.com/jquery-textcomplete/"
        :license     {"MIT" "http://opensource.org/licenses/MIT"}
        :scm         {:url "https://github.com/cljsjs/packages"}})

(deftask package []
  (comp
   (download :url (format "https://github.com/yuku-t/jquery-textcomplete/archive/v%s.zip" +lib-version+)
             :checksum "AC9EC52BBA4AE3EAB1C0030F004795C6"
             :unzip true)
    (sift :move {#"^jquery-textcomplete-[^\/]*/dist/jquery\.textcomplete\.js" "cljsjs/common/jquery-textcomplete.inc.js"
                 #"^jquery-textcomplete-[^\/]*/dist/jquery\.textcomplete\.min.js" "cljsjs/common/jquery-textcomplete.min.inc.js"})
   (sift :include #{#"^cljsjs"})
   (deps-cljs :name "cljsjs.jquery-textcomplete"
              :requires ["cljsjs.jquery"])
   (pom)
   (jar)))
