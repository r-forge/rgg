.packageName <- "rggPAMR"
.First.lib <- function(lib, pkg) {
	stylesheet <- paste(lib,pkg,"stylesheet","style.css",sep=.Platform$file.sep)
	assign("stylesheet",stylesheet, .GlobalEnv)
}