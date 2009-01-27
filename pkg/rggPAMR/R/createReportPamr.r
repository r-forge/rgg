#the function to create a report for the pamr gui. the argument list is long but the function should not be called explicitly
createReportPamr = function(rgghome,reportfolder,classes,nthresh,doPlotFDR,doPlotCVErrCurves,doCompConfMatrix,doPlotCVprob,doListSelGenes,thresh=NULL,fdr=NULL,cv=NULL,confusion=NULL,genes=NULL){
	if(!file.exists(reportfolder))
		dir.create(reportfolder)
	file.copy(get("stylesheet",.GlobalEnv),paste(reportfolder,"style.css",sep="/"), overwrite = T)
	report <- HTMLInitFile(reportfolder,filename="pamr-report",Title = "pamr-report generated by RGG GUI",CSSFile="style.css")
	
	#if running in a non-interactive software such as RGGRunner
	if(!dev.interactive()){		
		postscript(file="temp.eps")
		dev.control(displaylist='enable')	
	}
	
	#getting number of plots - R automatically sets T = 1 and F = 0
	nplots=doPlotFDR+doPlotCVErrCurves+doPlotCVprob
	
	nrppi = 72
  w=8
  h=8
  wd = w*nrppi
  hg = h*nrppi
	pbar = winProgressBar(title = "pamr-report", label = "Report creation: 0% done.", min = 0, max = nplots, initial = 0, width = 300)
	j=0
	
	HTML("<center><h1>PAMR-REPORT</h1>")
	HTML(paste("generated by RGG GUI, ",date(),"</center><br><br>"))
	HTML("<table border=\"0\" align=\"center\">")
	HTML(paste("<tr><th>Calculations done for column ",classes," and ",nthresh," different threshold values:</th></tr>",sep=""))
	if(!is.null(cv)){
		HTML("<tr><th class=\"sectionheader\"><h2>Cross-validation</h2></th></tr>")
		if(doPlotCVErrCurves){
			names = paste(reportfolder,"/","cverr",c(".pdf",".png"),sep="")
			
			pamr.plotcv(cv)
			dev.print(device=pdf,names[1],width=w,height=h,pointsize=14)
			dev.print(device=png,names[2],width=wd,height=hg,pointsize=10)
			if(dev.interactive()){
				dev.off()
			}
			
			j=j+1
			setWinProgressBar(pbar, j, label=paste("Report creation:",round(j/nplots*100),"% done."))
			HTML("<tr><td align=\"center\"><a href=\"cverr.pdf\"><img src=\"cverr.png\"/></a><br><br></td></tr>")
		}
		HTML("<tr><td>")
		HTML(as.data.frame(list(threshold=cv$threshold,nonzero=cv$size,error=cv$error)),Border=0,innerBorder=1,classfirstline="sectionheader",classfirstcolumn="sectionheader",digits=5,align="center",caption="Threshold values, number of remaining genes and error rates")
		write.table(as.data.frame(list(threshold=cv$threshold,nonzero=cv$size,error=cv$error)),file=paste(reportfolder,"threshold-list.txt",sep="/"),row.names=T,col.names=NA,sep="\t",na="NA")
		HTML("<br><br>")
		HTML("</tr></td>")
		
	}
	
	if(!is.null(fdr)){
		if(doPlotFDR){
			names = paste(reportfolder,"/","fdr",c(".pdf",".png"),sep="")
			
			pamr.plotfdr(fdr)
			dev.print(device=pdf,names[1],width=w,height=h,pointsize=14)
			dev.print(device=png,names[2],width=wd,height=hg,pointsize=10)
			if(dev.interactive()){
				dev.off()
			}
			
			j=j+1
			setWinProgressBar(pbar, j, label=paste("Report creation:",round(j/nplots*100),"% done."))
			HTML("<tr><th class=\"sectionheader\"><h2>False Discovery Rate</h2></th></tr>")
			HTML("<tr><td align=\"center\"><a href=\"fdr.pdf\"><img src=\"fdr.png\"/></a><br><br></td></tr>")
		}
		HTML("<tr><td>")
		HTML(fdr$results,Border=0,innerBorder=1,classfirstline="sectionheader",classfirstcolumn="sectionheader",digits=5,align="center",caption="False discovery rate")
		write.table(fdr$results,file=paste(reportfolder,"fdrresults.txt",sep="/"),row.names=T,col.names=NA,sep="\t",na="NA")
		HTML("</tr></td>")
	}
	
	if(!is.null(thresh) && !is.na(thresh)){
		HTML(paste("<tr><th class=\"sectionheader\">Model for selected threshold value: ",thresh,sep=""))
		if(!is.null(confusion)){
			HTML("<tr><td>")
			HTML(confusion,Border=0,innerBorder=1,classfirstline="sectionheader",classfirstcolumn="sectionheader",digits=5,align="center",caption="Confusion matrix")
			HTML("</tr></td>")
		}
		if(doPlotCVprob){
			names = paste(reportfolder,"/","cvprob",c(".pdf",".png"),sep="")
			
			pamr.plotcvprob(trained,data,thresh)
			dev.print(device=pdf,names[1],width=w,height=h,pointsize=14)
			dev.print(device=png,names[2],width=wd,height=hg,pointsize=10)
			if(dev.interactive()){
				dev.off()
			}

			j=j+1
			setWinProgressBar(pbar, j, label=paste("Report creation:",round(j/nplots*100),"% done."))
			HTML("<tr><td align=\"center\"><a href=\"cvprob.pdf\"><img src=\"cvprob.png\"/></a><br>Cross-validated sample probabilites per class</td></tr>")
		}
		HTML("<tr><td align=\"center\">")
		HTML(genes[1:min(nrow(genes),50),],Border=0,innerBorder=1,classfirstline="sectionheader",classfirstcolumn="sectionheader",digits=5,align="center",caption="List of remaining genes (up to 50 shown)")
		write.table(genes,file=paste(reportfolder,"remaininggenes.txt",sep="/"),row.names=T,col.names=NA,sep="\t",na="NA")
		HTML("</td></tr>")
	}
	
	HTML("</table>")
	HTML(paste("pamr-report generated by RGG GUI, ",date(),"<br><br>"))
	HTMLEndFile()
	close(pbar)

	#set the device off and delete the temp.eps
	if(!dev.interactive()){
		dev.off()
		file.remove("temp.eps")
	}
}