library(Biobase)
library(genefilter)
library(RColorBrewer)
library(lattice)
library(latticeExtra)
library(grid)

doHeatmap = function(eSet, intgroup,colourRange=rgb(seq(0,1,l=256),seq(0,1,l=256),seq(1,0,l=256)))
  {
	dat = exprs(eSet)
    outM = as.dist(dist2(dat))

    d.row = as.dendrogram(hclust(outM))
    od.row = order.dendrogram(d.row)
    m = as.matrix(outM)
    colnames(m) = colnames(dat)
    rownames(m) = colnames(dat)
		
    
   if(intgroup %in% names(phenoData(eSet)@data))
      {
        covar = pData(eSet)[colnames(pData(eSet))==intgroup][,1]
        lev = levels(as.factor(covar))
        corres = matrix(0,nrow=length(lev),ncol=2)
        colourCov = brewer.pal(12,"Set3")
				heatmap=levelplot(m[od.row,od.row],
                  scales=list(x=list(rot=90)),
                  legend=list(
                    top=list(fun=dendrogramGrob,args=list(x=d.row,side="top")),
                    right=list(fun=dendrogramGrob,args=list(x=d.row,side="right", size.add= 1, add = list(rect = list(col = "transparent", fill = colourCov[as.factor(covar)])), type = "rectangle"))),
                  colorkey = list(space ="left"),
                  xlab="",ylab="",
                  col.regions=colourRange)
				print(heatmap)
        
        x=0.06
        y=0.98
        
        for(i in seq_len(length(lev)))
          {
            corres[i,] = c(as.character(unique(covar[covar == lev[i]])),colourCov[i])
            grid.text(corres[i,1], x=x, y=y, just="left")
            grid.rect(gp=gpar(fill=corres[i,2],col="transparent"), x=x-0.02, y=y, width=0.02, height=0.02)
            y=y-0.03
          }
      } else {
        heatmap=levelplot(m[od.row,od.row],
                  scales=list(x=list(rot=90)),
                  legend=list(
                    top=list(fun=dendrogramGrob,args=list(x=d.row,side="top")),
                    right=list(fun=dendrogramGrob,args=list(x=d.row,side="right"))),
                  colorkey = list(space ="left"),
                  xlab="",ylab="",
                  col.regions=colourRange)
				print(heatmap)
      }
}