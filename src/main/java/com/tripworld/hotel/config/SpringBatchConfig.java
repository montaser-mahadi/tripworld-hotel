package com.tripworld.hotel.config;

import com.tripworld.hotel.model.Hotel;
import com.tripworld.hotel.model.Room;
import com.tripworld.hotel.repository.HotelRepository;
import com.tripworld.hotel.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class SpringBatchConfig {

    private JobBuilderFactory jobBuilderFactory;

    private StepBuilderFactory stepBuilderFactory;

    private HotelRepository hotelRepository;

    private RoomRepository roomRepository;


    @Bean
    public FlatFileItemReader<Hotel> hotelReader() {
        FlatFileItemReader<Hotel> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/main/resources/hotels.csv"));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapperHotel());
        return itemReader;
    }

    @Bean
    public FlatFileItemReader<Room> roomReader() {
        FlatFileItemReader<Room> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/main/resources/rooms.csv"));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapperRoom());
        return itemReader;
    }

    private LineMapper<Hotel> lineMapperHotel() {
        DefaultLineMapper<Hotel> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "hotelName", "description", "cityCode");

        BeanWrapperFieldSetMapper<Hotel> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Hotel.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    private LineMapper<Room> lineMapperRoom() {
        DefaultLineMapper<Room> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "description", "hotel");

        BeanWrapperFieldSetMapper<Room> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Room.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    @Bean
    public HotelProcessor hotelProcessor() {
        return new HotelProcessor();
    }

    @Bean
    public RoomProcessor roomProcessor() {
        return new RoomProcessor();
    }


    @Bean
    public RepositoryItemWriter<Hotel> writerHotel() {
        RepositoryItemWriter<Hotel> writer = new RepositoryItemWriter<>();
        writer.setRepository(hotelRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public RepositoryItemWriter<Room> writerRoom() {
        RepositoryItemWriter<Room> writer = new RepositoryItemWriter<>();
        writer.setRepository(roomRepository);
        writer.setMethodName("save");
        return writer;
    }
    @Bean
    public Step step1() {
        return stepBuilderFactory.get("csv-hotel").<Hotel, Hotel>chunk(10)
                .reader(hotelReader())
                .processor(hotelProcessor())
                .writer(writerHotel())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("csv-room").<Room, Room>chunk(10)
                .reader(roomReader())
                .processor(roomProcessor())
                .writer(writerRoom())
                .taskExecutor(taskExecutor())
                .build();
    }

   @Qualifier(value = "bulkData")
    @Bean
    public Job runJob() throws Exception {
        return jobBuilderFactory.get("bulkData")
                .start(step2())
                .next(step1())
                .build();
    }
/*
    @Qualifier(value = "bulkData")
    @Bean
    public Job runJob() {
        return jobBuilderFactory.get("bulkData")
                .flow(step2()).end().build();

    }*/

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(10);
        return asyncTaskExecutor;
    }


}
