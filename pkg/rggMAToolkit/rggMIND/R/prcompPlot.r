prcompPlot = function(eSet,group="none",ncomp=3){
	#if some values are missing, k nearest neighbor imputation fills them up, for the plot only
	if(any(is.na(exprs(eSet)))){
		cat("The values of some expressions are NA. Using k-nearest-neighbor imputation to fill these up for the principal components plot.")
		pc = prcomp(t(impute.knn(exprs(eSet))))
	}else{
		pc = prcomp(t(exprs(eSet)))
	}
	
	pcframe = as.data.frame(pc$x[,1:ncomp])
	varnames = paste(colnames(pc$x),paste(round((cumsum((pc$sdev)^2)/sum((pc$sdev)^2))*100,1),"%"),sep="\n Cumulative proportion \n of Variance: \n")
	varnames = varnames[1:ncomp]
	if(group != "none"){
    pcframe$group = phenoData(eSet)[[group]]
    ngroups = nlevels(as.factor(pcframe$group))
    super.sym <- trellis.par.get("superpose.symbol")
		super.sym$pch = letters[1:7]
		super.sym$cex=rep(1,7)
		trellis.par.set("superpose.symbol",super.sym)
	  key=list(title = paste("Plot of the first",ncomp,"principal components \n with respect to group",group), columns = ngroups, 
        points = list(col = super.sym$col[1:ngroups],pch = super.sym$pch[1:ngroups]),
        text = list(as.character(levels(as.factor(pcframe$group)))))
	title=paste("Plot of the first",ncomp,"principal components")
	print(splom(pcframe[,1:ncomp]~pcframe[,1:ncomp],group=pcframe$group,panel=panel.superpose,key=key,varnames=varnames))
	}else{
	title=paste("Plot of the first",ncomp,"principal components")
	print(splom(pcframe[,1:ncomp]~pcframe[,1:ncomp],main=title,varnames=varnames))
	}
}