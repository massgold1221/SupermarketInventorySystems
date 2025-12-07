package core.dto;

import java.io.Serializable;

/**
 * ReportDTO: Data Transfer Object for Reports
 */
public class ReportDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String reportType;
    private String generatedDate;
    private String filePath;
    private double totalInventoryValue;
    private int totalProductCount;
    private int lowStockCount;
    
    public ReportDTO() {}
    
    public ReportDTO(String reportType, String generatedDate) {
        this.reportType = reportType;
        this.generatedDate = generatedDate;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getReportType() {
        return reportType;
    }
    
    public void setReportType(String reportType) {
        this.reportType = reportType;
    }
    
    public String getGeneratedDate() {
        return generatedDate;
    }
    
    public void setGeneratedDate(String generatedDate) {
        this.generatedDate = generatedDate;
    }
    
    public String getFilePath() {
        return filePath;
    }
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public double getTotalInventoryValue() {
        return totalInventoryValue;
    }
    
    public void setTotalInventoryValue(double totalInventoryValue) {
        this.totalInventoryValue = totalInventoryValue;
    }
    
    public int getTotalProductCount() {
        return totalProductCount;
    }
    
    public void setTotalProductCount(int totalProductCount) {
        this.totalProductCount = totalProductCount;
    }
    
    public int getLowStockCount() {
        return lowStockCount;
    }
    
    public void setLowStockCount(int lowStockCount) {
        this.lowStockCount = lowStockCount;
    }
}
