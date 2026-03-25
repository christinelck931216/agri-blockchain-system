package service; // 放在 service 套件

import java.util.ArrayList; // 匯入 ArrayList
import java.util.HashMap; // 匯入 HashMap
import java.util.List; // 匯入 List
import java.util.Map; // 匯入 Map

import core.Blockchain; // 匯入 Blockchain
import core.ChainValidator; // 匯入驗證器
import model.Block; // 匯入 Block
import model.Participant; // 匯入 Participant
import model.ProductInfo; // 匯入 ProductInfo
import model.ProductTraceResult; // 匯入結果類
import model.TraceStep; // 匯入時間軸
import model.TransactionRecord; // 匯入交易紀錄

public class QueryService {

    private Blockchain blockchain; // 區塊鏈
    private Map<String, ProductInfo> productInfoMap; // 產品資料

    public QueryService(Blockchain blockchain) { // 建構子
        this.blockchain = blockchain; // 設定區塊鏈
        this.productInfoMap = new HashMap<>(); // 初始化 map
    }

    public void registerProductInfo(ProductInfo info) { // 註冊產品資料
        productInfoMap.put(info.getProductId(), info); // 存入 map
    }

    public ProductTraceResult getTraceByProductId(String productId) { // 查詢

        ProductTraceResult result = new ProductTraceResult(); // 建立結果

        result.setProductInfo(productInfoMap.get(productId)); // 設定基本資料

        boolean valid = ChainValidator.isChainValid(blockchain); // 驗證鏈
        result.setValid(valid); // 設定驗證結果

        List<TraceStep> steps = new ArrayList<>(); // 建立 timeline

        Block lastBlock = null; // 記錄最後區塊

        for (Block block : blockchain.getChain()) { // 跑每個區塊

            for (TransactionRecord record : block.getTransactions()) { // 跑交易

                if (record.getProductId().equals(productId)) { // 如果是該產品

                    Participant op = record.getOperator(); // 取得操作者

                    TraceStep step = new TraceStep(
                            record.getTimestamp(), // 時間
                            record.getActionType(), // 動作
                            record.getDescription(), // 描述
                            record.getLocation(), // 地點
                            op.getParticipantName(), // 操作者
                            op.getRole() // 角色
                    );

                    steps.add(step); // 加入 timeline

                    lastBlock = block; // 更新最後區塊
                }
            }
        }

        result.setTimeline(steps); // 設定 timeline

        if (lastBlock != null) { // 如果有資料
            result.setBlockNumber(lastBlock.getIndex()); // 設定區塊編號
            result.setBlockHash(lastBlock.getHash()); // 設定 hash
            result.setChainTime(lastBlock.getTimestamp()); // 設定時間
        }

        return result; // 回傳
    }
}