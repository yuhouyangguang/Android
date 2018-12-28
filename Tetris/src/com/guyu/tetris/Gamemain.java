package com.guyu.tetris;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class Gamemain extends Activity {
	int velocity;
	Button pause, moveleft, moveright, movedown, rotate;
	BlockAdapter nextTetrisAdapter, blockAdapter;
	TextView scoreview, level, speed, highest;
	int timeInterval = 800;
	int highestScore = 0;
	int ySize = 17;
	int xSize = 10;
	int score = 0;
	CacheUtils cacheUtils;
	int[][] blockColor = new int[ySize][xSize];
	int[] allBlock = new int[ySize];
	public String TAG = "Gamemain";
	GridView tetrisView, nextTetrisView;
	List<Integer> blockList = new ArrayList<Integer>();
	List<Integer> nextTetrisList = new ArrayList<Integer>();
	Random random;
	int rand, randColor;
	int nextRand, nextRandColor;
	Timer timer;
	int stop = 0;
	boolean isPause = false;
	int[] position = new int[] { -4, 4 }; // position[0] is the location of y.
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			for (int i = 0; i < ySize; i++) {
				if (allBlock[i] == 0) {
					for (int j = 0; j < xSize; j++) {
						blockList.set(i * xSize + j, 0);
					}
				} else {
					for (int j = 0; j < xSize; j++) {
						blockList.set(i * xSize + j, blockColor[i][j]);
					}
				}
			}
			boolean canMove = true;
			if (msg.what == 0) {
				position[0]++;
				for (int i = 3; i >= 0; i--) {
					int line = i + position[0];
					if (line >= 0 && Square.shape[rand][i] != 0) {
						if (line >= ySize || ((allBlock[line] & (leftMath(Square.shape[rand][i], position[1]))) != 0)) {
							canMove = false;
							break;
						}
					}
				}
				if (!canMove) {
					position[0]--;
					for (int i = 3; i >= 0; i--) {
						int line = i + position[0];
						if (line >= 0 && Square.shape[rand][i] != 0) {
							for (int j = 0; j < xSize; j++) {
								if (((1 << j) & (leftMath(Square.shape[rand][i], position[1]))) != 0) {
									blockList.set(line * xSize + j, randColor);
								}
							}
						}
					}
					stopDown();
				} else {
					for (int i = 3; i >= 0; i--) {
						int line = i + position[0];
						if (line >= 0 && Square.shape[rand][i] != 0) {
							for (int j = 0; j < xSize; j++) {
								if (((1 << j) & (leftMath(Square.shape[rand][i], position[1]))) != 0) {
									blockList.set(line * xSize + j, randColor);
								}
							}
						}
					}
				}
			} else {
				for (int i = 3; i >= 0; i--) {
					int line = i + position[0];
					if (line >= 0 && Square.shape[rand][i] != 0) {
						for (int j = 0; j < xSize; j++) {
							if (((1 << j) & (leftMath(Square.shape[rand][i], position[1]))) != 0) {
								blockList.set(line * xSize + j, randColor);
							}
						}
					}
				}
			}
			blockAdapter.setmDatas(blockList);
			blockAdapter.notifyDataSetChanged();
		}
	};

	private void startTimer() {
		if (timer == null) {
			timer = new Timer();
		}
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				handler.sendEmptyMessage(0);
			}
		}, 0, timeInterval);
	}

	private void stopTimer() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	@SuppressWarnings("unchecked")
	private void nextTetrisShow() {
		nextTetrisList.clear();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (((1 << j) & Square.shape[nextRand][i]) != 0) {
					nextTetrisList.add(nextRandColor);
				} else {
					nextTetrisList.add(0);
				}
			}
		}
		nextTetrisAdapter.setmDatas(nextTetrisList);
		nextTetrisAdapter.notifyDataSetChanged();
	}

	int leftMath(int a, int b) {
		if (b < 0) {
			return a >> -b;
		} else {
			return a << b;
		}
	}

	private void pause() {
		isPause = !isPause;
		if (isPause) {
			stopTimer();
			pause.setText("continue");
			moveleft.setEnabled(false);
			moveright.setEnabled(false);
			rotate.setEnabled(false);
			movedown.setEnabled(false);
		} else {
			startTimer();
			pause.setText("pause");
			moveleft.setEnabled(true);
			moveright.setEnabled(true);
			rotate.setEnabled(true);
			movedown.setEnabled(true);
		}
	}

	private void gameOver() {
		cacheUtils.putValue("highestScore" + velocity, String.valueOf(highestScore));
		stopTimer();
		AlertDialog.Builder dialog = new AlertDialog.Builder(Gamemain.this);
		dialog.setTitle("Gameover");
		dialog.setMessage("You have got " + score);
		dialog.setPositiveButton("Play again", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				stop = 0;
				position[0] = -4;
				position[1] = 4;
				for (int i = 0; i < ySize; i++) {
					allBlock[i] = 0;
					for (int j = 0; j < xSize; j++) {
						blockColor[i][j] = 0;
					}
				}
				rand = random.nextInt(19);
				position[0] = Square.initPosition[rand][1];
				position[1] = Square.initPosition[rand][0];
				randColor = random.nextInt(5) + 1;

				nextRand = random.nextInt(19);
				nextRandColor = random.nextInt(5) + 1;

				timer = new Timer();
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						handler.sendEmptyMessage(0);
					}
				}, 0, timeInterval);
			}
		});
		dialog.setNegativeButton("Leave", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		}).create();
		dialog.setCancelable(false);
		dialog.show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_gamemain);
		Intent intent = getIntent();
		velocity = intent.getIntExtra("rank", 3);
		switch (velocity) {
		case 1:
			timeInterval = 1200;
			break;
		case 2:
			timeInterval = 1000;
			break;
		case 3:
			timeInterval = 800;
			break;
		case 4:
			timeInterval = 600;
			break;
		case 5:
			timeInterval = 400;
			break;
		default:
			break;
		}
		cacheUtils = new CacheUtils(this, "UserInfo");
		String maxString = "";
		try {
			maxString = cacheUtils.getValue("highestScore" + velocity, String.valueOf(0));
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
		try {
			highestScore = Integer.parseInt(maxString.toString());
		} catch (NumberFormatException e) {
			highestScore = 0;
		}
		moveleft = (Button) findViewById(R.id.left_move);
		moveright = (Button) findViewById(R.id.right_move);
		rotate = (Button) findViewById(R.id.rotate_move);
		movedown = (Button) findViewById(R.id.down_move);
		scoreview = (TextView) findViewById(R.id.score1);
		highest = (TextView) findViewById(R.id.highest);
		level = (TextView) findViewById(R.id.level1);
		speed = (TextView) findViewById(R.id.speed1);
		highest.setText(highestScore + "");
		scoreview.setText(score + "");
		level.setText(velocity + "");
		speed.setText((1200.0/timeInterval) + "");
		moveleft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				for (int i = 3; i >= 0; i--) {
					if ((((leftMath(Square.shape[rand][i], position[1])) >> 1) << 1) != (leftMath(Square.shape[rand][i],
							position[1]))) {
						return;
					}
				}
				for (int i = 3; i >= 0; i--) {
					int line = i + position[0];
					if (line >= 0 && Square.shape[rand][i] != 0) {
						if ((allBlock[line] & (leftMath(Square.shape[rand][i], position[1]) >> 1)) != 0) {
							return;
						}
					}
				}
				position[1]--;
				handler.sendEmptyMessage(1);
			}
		});
		moveright.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				for (int i = 3; i >= 0; i--) {
					if (((leftMath(Square.shape[rand][i], position[1])) << 1) > 0x3ff) {
						return;
					}
				}
				for (int i = 3; i >= 0; i--) {
					int line = i + position[0];
					if (line >= 0 && Square.shape[rand][i] != 0) {
						if ((allBlock[line] & (leftMath(Square.shape[rand][i], position[1]) << 1)) != 0) {
							return;
						}
					}
				}
				position[1]++;
				handler.sendEmptyMessage(1);
			}
		});
		rotate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int nextRotate = Square.nextShape[rand];
				for (int i = 3; i >= 0; i--) {
					int line = i + position[0];
					if (leftMath(Square.shape[nextRotate][i], position[1]) > 0x3ff) {
						return;
					} else if (Square.shape[nextRotate][i] > 0 && line >= ySize) {
						return;
					} else if (leftMath(leftMath(Square.shape[nextRotate][i], position[1]), -position[1]) != Square.shape[nextRotate][i]) {
						return;
					} else if (line > 0 && line < ySize && (leftMath(Square.shape[nextRotate][i], position[1]) & allBlock[line]) != 0) {
						return;
					}
				}
				rand = nextRotate;
				handler.sendEmptyMessage(1);
			}
		});
		movedown.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int down = 1 << 10;
				for (int i = 3; i >= 0; i--) {
					int line = i + position[0];
					if (line >= 0 && Square.shape[rand][i] != 0) {
						down = Math.min(down, ySize - line - 1);
						for (int j = 0; j < xSize; j++) {
							if (((1 << j) & (leftMath(Square.shape[rand][i], position[1]))) != 0) {
								for (int k = 0; k + line < ySize; k++) {
									if (blockColor[k + line][j] > 0) {
										down = Math.min(down, k - 1);
										break;
									}
								}
							}
						}
					}
				}
				if (down <= 0 || down == (1 << 10)) {
					return;
				} else {
					position[0] += down;
					handler.sendEmptyMessage(0);
				}
			}
		});
		tetrisView = (GridView) findViewById(R.id.tetrisView);
		nextTetrisView = (GridView) findViewById(R.id.nextTetrisView);
		pause = (Button) findViewById(R.id.pause);
		pause.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				pause();
			}
		});
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 17; j++) {
				blockList.add(0);
			}
		}
		blockAdapter = new BlockAdapter(Gamemain.this, blockList, R.layout.item_adapter);
		tetrisView.setAdapter(blockAdapter);
		random = new Random();
		rand = random.nextInt(19);
		position[0] = Square.initPosition[rand][1];
		position[1] = Square.initPosition[rand][0];
		randColor = random.nextInt(5) + 1;
		nextRand = random.nextInt(19);
		nextRandColor = random.nextInt(5) + 1;
		nextTetrisList.clear();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (((1 << j) & Square.shape[nextRand][i]) != 0) {
					nextTetrisList.add(nextRandColor);
				} else {
					nextTetrisList.add(0);
				}
			}
		}
		nextTetrisAdapter = new BlockAdapter(Gamemain.this, nextTetrisList, R.layout.item_adapter);
		nextTetrisView.setAdapter(nextTetrisAdapter);
		Log.i(TAG, rand + "");
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				handler.sendEmptyMessage(0);
			}
		}, 0, timeInterval);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		stopTimer();
	}
	private void stopDown() {
		for (int i = 3; i >= 0; i--) {
			int line = i + position[0];
			if (line >= 0 && Square.shape[rand][i] != 0) {
				allBlock[line] += (leftMath(Square.shape[rand][i], position[1]));
				for (int j = 0; j < xSize; j++) {
					if (((1 << j) & (leftMath(Square.shape[rand][i], position[1]))) != 0) {
						blockColor[line][j] = randColor;
					}
				}
			}
		}
		for (int i = ySize - 1; i >= 0;) {
			if (allBlock[i] == 0x3ff) {
				score++;
				scoreview.setText(score + "");
				for (int j = i - 1; j >= 0; j--) {
					allBlock[j + 1] = allBlock[j];
					for (int k = 0; k < xSize; k++) {
						blockColor[j + 1][k] = blockColor[j][k];
					}
				}
				allBlock[0] = 0;
				for (int j = 0; j < xSize; j++) {
					blockColor[0][j] = 0;
				}
			} else {
				i--;
			}
		}
		if (allBlock[0] != 0) {
			if (score > highestScore) {
				cacheUtils.getValue("highestScore" + velocity, score + "");
				highestScore = score;
				highest.setText(highestScore + "");
				scoreview.setText(score + "");

			}

			gameOver();
		}
		rand = nextRand;
		position[0] = Square.initPosition[rand][1];
		position[1] = Square.initPosition[rand][0];
		randColor = nextRandColor;

		nextRand = random.nextInt(19);
		nextRandColor = random.nextInt(5) + 1;
		nextTetrisShow();
		Log.i(TAG, rand + "");
	}
}
