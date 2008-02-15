.packageName <- "RGG"
# library initialization:
.First.lib <- function(lib, pkg) {
  library(rJava)
  #if(!.jgr.works){
   # cat("\nThis package is developed only for running from in JGR\n")
    #return(TRUE)
  #}
  
  #add libraries (.jar) to the classpath
  for(var in list.files(paste(lib, pkg, "lib",sep=.Platform$file.sep))){
    .jaddClassPath(paste(lib,pkg,"lib",var,sep=.Platform$file.sep))
  } 

  jgr.addMenu("RGG")
  jgr.addMenuItem("RGG","Load",".jcall(rggloader,,\"load\")")
  rgg <- .jnew("org/arcs/rgg/jgr/RGGMenu")
  assign("rggloader",rgg, .GlobalEnv)
}
