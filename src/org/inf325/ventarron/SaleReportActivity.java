package org.inf325.ventarron;

import java.util.List;

import org.inf325.ventarron.adapters.SaleReportAdapter;
import org.inf325.ventarron.dao.DbHelper;
import org.inf325.ventarron.dao.Sale;
import org.inf325.ventarron.services.SaleService;
import org.inf325.ventarron.services.SaleServiceImpl;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

import static org.inf325.ventarron.utils.Constants.*;

public class SaleReportActivity extends OrmLiteBaseActivity<DbHelper> {
	private ListView saleReportListView;
	private int reportFilterId;
	private int reportMode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sale_report);

		loadExtraData();
		loadViews();
		initializeData();
	}

	private void loadExtraData() {
		Intent intent = getIntent();
		reportMode = intent.getIntExtra(EXTRA_REPORT_MODE, 0);
		reportFilterId = intent.getIntExtra(EXTRA_REPORT_FILTER_ID, 0);
	}

	private void loadViews() {
		saleReportListView = (ListView) findViewById(R.id.salereport_list);
	}

	private void initializeData() {
		SaleService service = SaleServiceImpl.createService(getHelper());
		switch (reportMode) {
		case SALE_REPORT_CLIENT:
			clientSaleReport(service);
			break;
		case SALE_REPORT_PRODUCT:
			productSaleReport(service);
			break;
		case SALE_REPORT_SELLER:
			sellerSaleReport(service);
			break;
		}
	}

	private void clientSaleReport(SaleService service) {
		List<Sale> saleList = service.clientSaleReport(reportFilterId);
		setListViewAdapter(saleList);
	}

	private void productSaleReport(SaleService service) {
		List<Sale> saleList = service.productSaleReport(reportFilterId);
		setListViewAdapter(saleList);
	}

	private void sellerSaleReport(SaleService service) {
		List<Sale> saleList = service.sellerSaleReport(reportFilterId);
		setListViewAdapter(saleList);
	}

	private void setListViewAdapter(List<Sale> saleList) {
		SaleReportAdapter adapter = new SaleReportAdapter(this,
				R.layout.salereport_item_row, saleList);
		
		saleReportListView.setAdapter(adapter);
	}
}
