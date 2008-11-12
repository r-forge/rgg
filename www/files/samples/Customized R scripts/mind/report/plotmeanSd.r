library(Biobase)
library(genefilter)

plotmeanSd = function(eSet, ranks=TRUE, do.logtransform=F,xlab = ifelse(ranks, "rank(mean)", "mean"), ylab = "sd", ylim=c(0,2), pch  = ".", plot = TRUE, ...) {
#function slightly modified from vsn package
     
    stopifnot(is.logical(ranks), length(ranks)==1, !is.na(ranks))
	
	#if specified, logtransform
	x = exprs(eSet)
	if(do.logtransform){
		x = log2(x)
		x[x == -Inf] <- NA
	}
	
    n = nrow(x)
    if(n==0L) {
		warning("In 'meanSdPlot': matrix has 0 rows, there is nothing to be done.")
        return()
    }
      
    px   = rowMeans(x, na.rm=TRUE)
    py   = rowVars(x, na.rm=TRUE)
    rpx  = rank(px, na.last=FALSE, ties.method = "random")
    if(range(py,na.rm=T,finite=T)[2]>2){
	}
    ## run median with centers at dm,2*dm,3*dm,... and width 2*dm
    dm        = 0.05
    midpoints = seq(dm, 1-dm, by=dm)
    within    = function(x, x1, x2) { x>=x1 & x<=x2 }
    mediwind  = function(mp) median(py[within(rpx/n, mp-dm,
                                                           mp+dm)], na.rm=TRUE)
    rq.sds    = sapply(midpoints, mediwind)
                
    if(ranks) {
		px  = rpx
		res = list(rank=midpoints*n, sd=rq.sds)
    } else {
		res = list(quantile=quantile(px, probs=midpoints, na.rm=TRUE), sd=rq.sds)
    }
      
    if(plot) {
		plot(px, py, pch=pch, xlab=xlab, ylab=ylab,ylim=ylim, ...)
        lines(res[[1]], res[[2]], col="red", type="b", pch=19)
    }
}