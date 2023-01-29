package com.tugasakhir.welearn.data

import com.tugasakhir.welearn.utils.DataMapper
import com.tugasakhir.welearn.data.source.remote.IMultiplayerDataSource
import com.tugasakhir.welearn.data.source.remote.network.ApiResponse
import com.tugasakhir.welearn.domain.entity.*
import com.tugasakhir.welearn.domain.repository.IMultiPlayerRepository
import kotlinx.coroutines.flow.*

class MultiPlayerRepository constructor(
    private val remoteDataSource: IMultiplayerDataSource
): IMultiPlayerRepository {

    override fun getIDSoalMulti(jenis: Int, level: Int, authToken: String) =
        remoteDataSource.getIDSoalMulti(jenis, level, authToken).map {
            when(it) {
                is ApiResponse.Success -> {
                    Resource.Success(DataMapper.mapIDSoalMulti(it.data))
                }
                is ApiResponse.Empty -> {
                    Resource.Success(it)
                }
                is ApiResponse.Error -> {
                    Resource.Error(it.errorMessage)
                }
            } as Resource<IDSoalMulti>
        }

    override fun getSoalByID(id: Int, authToken: String) =
        remoteDataSource.getSoalByID(id, authToken).map {
            when(it) {
                is ApiResponse.Success -> {
                    Resource.Success(DataMapper.mapperSoal(it.data))
                }
                is ApiResponse.Empty -> {
                    Resource.Success(it)
                }
                is ApiResponse.Error -> {
                    Resource.Error(it.errorMessage)
                }
            } as Resource<Soal>
        }

    override fun pushNotification(body: PushNotification) =
        remoteDataSource.pushNotification(body).map {
            when(it) {
                is ApiResponse.Success -> {
                    Resource.Success(DataMapper.mapperPushNotification(it.data))
                }
                is ApiResponse.Empty -> {
                    Resource.Success(it)
                }
                is ApiResponse.Error -> {
                    Resource.Error(it.errorMessage)
                }
            } as Resource<String>
        }

    override fun makeRoom(id_jenis: Int, id_level: Int, authToken: String) =
        remoteDataSource.makeRoom(id_jenis, id_level, authToken).map {
            when(it) {
                is ApiResponse.Success -> {
                    Resource.Success(DataMapper.mapperRoom(it.data.data!!))
                }
                is ApiResponse.Empty -> {
                    Resource.Success(it)
                }
                is ApiResponse.Error -> {
                    Resource.Error(it.errorMessage)
                }
            } as Resource<Room>
        }

    override fun joinGame(idRoom: Int, authToken: String) =
        remoteDataSource.joinGame(idRoom, authToken).map {
            when(it) {
                is ApiResponse.Success -> {
                    Resource.Success(DataMapper.mapperJoinGame(it.data))
                }
                is ApiResponse.Empty -> {
                    Resource.Success(it)
                }
                is ApiResponse.Error -> {
                    Resource.Error(it.errorMessage)
                }
            } as Resource<JoinGame>
        }

    override fun gameAlreadyEnd(idGame: String, authToken: String) =
        remoteDataSource.gameAlreadyEnd(idGame, authToken).map {
            when(it) {
                is ApiResponse.Success -> {
                    Resource.Success(DataMapper.mapperGameAlreadyEnd(it.data))
                }
                is ApiResponse.Empty -> {
                    Resource.Success(it)
                }
                is ApiResponse.Error -> {
                    Resource.Error(it.errorMessage)
                }
            } as Resource<String>
        }

    override fun forceEndGame(idGame: String, authToken: String) =
        remoteDataSource.forceEndGame(idGame, authToken).map {
            when(it) {
            is ApiResponse.Success -> {
                Resource.Success(DataMapper.mapperForcedEndGame(it.data))
            }
            is ApiResponse.Empty -> {
                Resource.Success(it)
            }
            is ApiResponse.Error -> {
                Resource.Error(it.errorMessage)
            }
        } as Resource<String> }

    override fun getListScoreMulti(idGame: Int, authToken: String) =
        remoteDataSource.getListScoreMulti(idGame, authToken).map {
            when(it) {
                is ApiResponse.Success -> {
                    Resource.Success(DataMapper.mapperScoreMulti(it.data))
                }
                is ApiResponse.Empty -> {
                    Resource.Success(it)
                }
                is ApiResponse.Error -> {
                    Resource.Error(it.errorMessage)
                }
            } as Resource<List<ScoreMulti>>
        }

    override fun savePredictHurufMulti(
        idGame: Int,
        idSoal: Int,
        score: Int,
        duration: Int,
        authToken: String
    ) = remoteDataSource.savePredictHurufMulti(idGame, idSoal,score, duration, authToken).map {
        when(it) {
            is ApiResponse.Success -> {
                Resource.Success(DataMapper.mapperPredictMulti(it.data))
            }
            is ApiResponse.Empty -> {
                Resource.Success(it)
            }
            is ApiResponse.Error -> {
                Resource.Error(it.errorMessage)
            }
        } as Resource<SavePredictMulti>
    }

    override fun savePredictAngkaMulti(
        idGame: Int,
        idSoal: Int,
        score: Int,
        duration: Int,
        authToken: String
    ) = remoteDataSource.savePredictAngkaMulti(idGame, idSoal, score, duration, authToken).map {
        when(it) {
            is ApiResponse.Success -> {
                Resource.Success(DataMapper.mapperPredictMulti(it.data))
            }
            is ApiResponse.Empty -> {
                Resource.Success(it)
            }
            is ApiResponse.Error -> {
                Resource.Error(it.errorMessage)
            }
        } as Resource<SavePredictMulti>
    }

    override fun getListUserJoin(authToken: String) =
        remoteDataSource.getListUserJoin(authToken).map {
            when(it) {
                is ApiResponse.Success -> {
                    Resource.Success(DataMapper.mapperJoinedGame(it.data))
                }
                is ApiResponse.Empty -> {
                    Resource.Success(it)
                }
                is ApiResponse.Error -> {
                    Resource.Error(it.errorMessage)
                }
            } as Resource<List<UserJoinMulti>>
        }

    override fun getListUserParticipant(idGame: Int, authToken: String) =
        remoteDataSource.getListUserParticipant(idGame, authToken).map {
            when(it) {
                is ApiResponse.Success -> {
                    Resource.Success(DataMapper.mapperUserParticipant(it.data))
                }
                is ApiResponse.Empty -> {
                    Resource.Success(it)
                }
                is ApiResponse.Error -> {
                    Resource.Error(it.errorMessage)
                }
            } as Resource<List<UserPaticipantMulti>>
        }
}