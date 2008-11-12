library(limma)
library(geneplotter)

plotMADual <- function(MA, array1=1, genesToLabel=NULL, labelCol="red", foldLine=2,labelpch=16,ma.ylim=2,sampleSize=NULL,...){
#function plotma from beadarray package modified

#get the data
  M = MA$M[,array1]
  A = MA$A[,array1]
  xlimMA = quantile(A, probs=1e-4*c(1,-1)+c(0,1), na.rm=TRUE)
  ylimMA = quantile(M, probs=1e-4*c(1,-1)+c(0,1), na.rm=TRUE)
	naInd = intersect(which(!is.na(M)),which(!is.na(A)))
	naInd = intersect(naInd, which(!(is.infinite(M))))
	naInd = intersect(naInd, which(!(is.infinite(A))))
	
  smoothScatter(A[naInd],M[naInd], pch=16,cex=0.4, xlim=xlimMA,ylim=ylimMA, xlab = "", ylab = "", ...) 

  abline(h=c(-log2(foldLine),0,log2(foldLine)),lty=c(2,1,2)) 
  
  if(!is.null(genesToLabel)){

    index = which(rownames(exprs) %in% genesToLabel)

    points(A[index], M[index], col=labelCol, pch=labelpch)
  }

}