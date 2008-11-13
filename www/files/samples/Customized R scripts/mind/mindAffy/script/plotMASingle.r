plotMASingle <- function(eSet, array1=1,xlim,ylim, genesToLabel=NULL, labelCol="red", foldLine=2, do.logtransform=FALSE,labelpch=16,ma.ylim=2,sampleSize=NULL,...){
#function plotma from beadarray package modified
#get the data
dat = exprs(eSet)

#if specified, logtransform
  if(do.logtransform){
    dat = log2(dat)
    dat[dat == -Inf] <- NA
  }

  y = apply(dat,1,median,na.rm=T)
  x = dat[,array1]
  M = x-y
  A = 0.5*(x+y)

	naInd = intersect(which(!is.na(M)),which(!is.na(A)))
	naInd = intersect(naInd, which(!(is.infinite(M))))
	naInd = intersect(naInd, which(!(is.infinite(A))))
	
  smoothScatter(A[naInd],M[naInd], pch=16,cex=0.4, , xlab = "", ylab = "", xlim=xlim,ylim=ylim,...) 

  abline(h=c(-log2(foldLine),0,log2(foldLine)),lty=c(2,1,2)) 
  
  if(!is.null(genesToLabel)){

    index = which(rownames(exprs) %in% genesToLabel)

    points(A[index], M[index], col=labelCol, pch=labelpch)
  }

}